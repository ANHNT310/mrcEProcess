package com.bpm.mrceprocess.service.impl;

import com.bpm.dtos.LazyLoadEventDTO;
import com.bpm.enums.ApplicationMessage;
import com.bpm.exception.ApplicationException;
import com.bpm.mrceprocess.common.consts.ApplicationConst;
import com.bpm.mrceprocess.common.dtos.*;
import com.bpm.mrceprocess.common.enums.GeneralInformationType;
import com.bpm.mrceprocess.common.enums.ProcessScopeEnum;
import com.bpm.mrceprocess.external.DocumentService;
import com.bpm.mrceprocess.external.WorkflowService;
import com.bpm.mrceprocess.external.payload.WorkflowTaskSummaryDTO;
import com.bpm.mrceprocess.mapping.*;
import com.bpm.mrceprocess.persistence.entity.GeneralInformation;
import com.bpm.mrceprocess.persistence.entity.GeneralInformationHistory;
import com.bpm.mrceprocess.persistence.entity.GeneralInformationHistoryTicket;
import com.bpm.mrceprocess.persistence.entity.ProcessDetailInformationView;
import com.bpm.mrceprocess.persistence.repository.*;
import com.bpm.mrceprocess.persistence.specification.GeneralInformationHistorySpecification;
import com.bpm.mrceprocess.persistence.specification.GeneralInformationHistoryTicketSpecification;
import com.bpm.mrceprocess.persistence.specification.GeneralInformationSpecification;
import com.bpm.mrceprocess.security.AuthenticateComponent;
import com.bpm.mrceprocess.service.ProcessViewService;
import com.bpm.utils.FilterSpecification;
import com.bpm.utils.PageableHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import feign.Response;

import com.bpm.mrceprocess.mapping.*;
import com.bpm.mrceprocess.persistence.entity.*;
import com.bpm.mrceprocess.persistence.repository.*;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProcessViewServiceImpl implements ProcessViewService {

    private final AuthenticateComponent authenticateComponent;
    private final ProcessDetailInformationViewRepository processDetailInformationViewRepository;
    private final ProcessDetailInformationViewMapper processDetailInformationViewMapper;
    private final WorkflowService workflowService;
    private final GeneralInformationHistoryRepository generalInformationHistoryRepository;
    private final ProcessDetailDTOMapper processDetailDTOMapper;
    private final GeneralInformationHistoryMapper generalInformationHistoryMapper;
    private final GeneralInformationRepository generalInformationRepository;
    private final ProcessDetailInformationPendingDTOMapper processDetailInformationPendingDTOMapper;
    private final GeneralInformationHistoryTicketRepository generalInformationHistoryTicketRepository;
    private final ViewProcessDTOMapper viewProcessDTOMapper;
    private final DocumentService documentService;
    private final TermAbbreviationRepository termAbbreviationRepository;
    private final TermAbbreviationMapper termAbbreviationMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<ViewProcessDTO> byUser(LazyLoadEventDTO eventDTO) {
        String userId = authenticateComponent.getUserId();
        Specification<GeneralInformationHistory> combinedSpec = GeneralInformationHistorySpecification.filterByCreateBy(userId, eventDTO);
        Pageable pageable = PageableHelper.createPageable(eventDTO);
        return generalInformationHistoryRepository.findAll(combinedSpec, pageable)
                .map(viewProcessDTOMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProcessDetailInformationPendingDTO> pending(LazyLoadEventDTO eventDTO) {
        // 1. Get all pending tasks from the workflow service.
        List<WorkflowTaskSummaryDTO> pending = workflowService.pending(ApplicationConst.WORKFLOW_TENANT);

        if (pending.isEmpty()) {
            return Page.empty(PageableHelper.createPageable(eventDTO));
        }

        // 2. Create a map for efficient lookup and get a list of business codes.
        Map<String, WorkflowTaskSummaryDTO> pendingTasksMap = pending.stream()
                .collect(Collectors.toMap(WorkflowTaskSummaryDTO::getBusinessKey, task -> task, (first, second) -> first));
        List<String> businessCodes = new ArrayList<>(pendingTasksMap.keySet());

        // 3. Use the dedicated specification to filter tickets based on pending business codes and the global filter.
        String globalFilter = eventDTO.getGlobalFilter();
        Specification<GeneralInformationHistoryTicket> spec =
                GeneralInformationHistoryTicketSpecification.createFilter(businessCodes, globalFilter);

        // 4. Create pageable and query for a page of tickets.
        Pageable pageable = PageableHelper.createPageable(eventDTO);
        Page<GeneralInformationHistoryTicket> ticketsPage = generalInformationHistoryTicketRepository.findAll(spec, pageable);

        return ticketsPage.map(ticket -> {
            GeneralInformationHistory history = ticket.getInformationHistory();
            WorkflowTaskSummaryDTO summaryDTO = pendingTasksMap.getOrDefault(ticket.getBusinessCode(), null);
            return processDetailInformationPendingDTOMapper.toDTO(history, summaryDTO);
        });
    }

    @Override
    @Transactional(readOnly = true)
    public ProcessDetailDTO detail(String processDetailId) {
        GeneralInformationHistory generalInformationHistory = generalInformationHistoryRepository.findById(processDetailId)
                .orElseThrow(() -> new ApplicationException(ApplicationMessage.NOT_FOUND));
        return processDetailDTOMapper.toDTO(generalInformationHistory);
    }

    @Override
    public Page<GeneralInformationHistoryDTO> histories(String generalId, LazyLoadEventDTO eventDTO) {
        Specification<GeneralInformationHistory> historySpecification = (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("general_information_id"), generalId);

        Specification<GeneralInformationHistory> filterSpec = new FilterSpecification<>(eventDTO);

        Specification<GeneralInformationHistory> combinedSpec = historySpecification.and(filterSpec);

        Pageable pageable = PageableHelper.createPageable(eventDTO);

        return generalInformationHistoryRepository.findAll(combinedSpec, pageable)
                .map(generalInformationHistoryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public ProcessDetailDTO availableDetail(String generalId) {
        GeneralInformation generalInformation = generalInformationRepository.findById(generalId)
                .orElseThrow(() -> new ApplicationException(ApplicationMessage.NOT_FOUND));
        if (generalInformation.getAvailable() == null) {
            return null;
        }

        return processDetailDTOMapper.toDTO(generalInformation.getAvailable());
    }

    @Override
    public Page<ViewProcessDTO> availableByScope(GeneralInformationType type, LazyLoadEventDTO eventDTO) {
        Specification<GeneralInformation> combinedSpec = GeneralInformationSpecification.filterAvailableAndByType(type.name(), eventDTO);
        Pageable pageable = PageableHelper.createPageable(eventDTO);
        return generalInformationRepository.findAll(combinedSpec, pageable).map(viewProcessDTOMapper::toDTO);
    }

    @Override
    public Resource generateExcel(String processDetailId, String templateCode) {
        List<TermAbbreviation> termAbbreviations = termAbbreviationRepository.findByGeneralInformationHistoryId(processDetailId);
        if (termAbbreviations.isEmpty()) {
            throw new IllegalArgumentException("No Term Abbreviation data found to export to Excel.");
        }
        List<TermAbbreviationDTO> termAbbreviationDTOS = termAbbreviations.stream()
                .map(termAbbreviationMapper::toDTO)
                .toList();

        Response response = documentService.exportExcel(termAbbreviationDTOS, templateCode);

        try (InputStream inputStream = response.body().asInputStream()) {
            byte[] fileBytes = inputStream.readAllBytes();
            return new ByteArrayResource(fileBytes);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read Excel response from document service", e);
        }
    }

}