package com.bpm.mrceprocess.service.impl;

import com.bpm.dtos.LazyLoadEventDTO;
import com.bpm.enums.ApplicationMessage;
import com.bpm.exception.ApplicationException;
import com.bpm.mrceprocess.common.consts.ApplicationConst;
import com.bpm.mrceprocess.common.dtos.GeneralInformationDTO;
import com.bpm.mrceprocess.common.dtos.GeneralInformationHistoryDTO;
import com.bpm.mrceprocess.common.dtos.ProcessDetailDTO;
import com.bpm.mrceprocess.common.dtos.ProcessDetailInformationViewDTO;
import com.bpm.mrceprocess.common.enums.ProcessScopeEnum;
import com.bpm.mrceprocess.external.WorkflowService;
import com.bpm.mrceprocess.external.payload.WorkflowTaskSummaryDTO;
import com.bpm.mrceprocess.mapping.GeneralInformationHistoryMapper;
import com.bpm.mrceprocess.mapping.GeneralInformationMapper;
import com.bpm.mrceprocess.mapping.ProcessDetailDTOMapper;
import com.bpm.mrceprocess.mapping.ProcessDetailInformationViewMapper;
import com.bpm.mrceprocess.persistence.entity.GeneralInformation;
import com.bpm.mrceprocess.persistence.entity.GeneralInformationHistory;
import com.bpm.mrceprocess.persistence.entity.ProcessDetailInformationView;
import com.bpm.mrceprocess.persistence.entity.ProcessScopeConfig;
import com.bpm.mrceprocess.persistence.repository.GeneralInformationHistoryRepository;
import com.bpm.mrceprocess.persistence.repository.GeneralInformationRepository;
import com.bpm.mrceprocess.persistence.repository.ProcessDetailInformationViewRepository;
import com.bpm.mrceprocess.persistence.repository.ProcessScopeConfigRepository;
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

import java.util.List;
import java.util.Map;
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
    private final ProcessScopeConfigRepository processScopeConfigRepository;
    private final GeneralInformationMapper generalInformationMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<ProcessDetailInformationViewDTO> byUser(LazyLoadEventDTO eventDTO) {
        String userId = authenticateComponent.getUserId();

        Specification<ProcessDetailInformationView> userSpec = (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("createdBy"), userId);

        Specification<ProcessDetailInformationView> filterSpec = new FilterSpecification<>(eventDTO);

        Specification<ProcessDetailInformationView> combinedSpec = userSpec.and(filterSpec);

        Pageable pageable = PageableHelper.createPageable(eventDTO);

        return processDetailInformationViewRepository.findAll(combinedSpec, pageable)
                .map(processDetailInformationViewMapper::toDto);
    }

    @Override
    public Page<ProcessDetailInformationViewDTO> pending(LazyLoadEventDTO eventDTO) {
        List<WorkflowTaskSummaryDTO> pending = workflowService.pending(ApplicationConst.WORKFLOW_TENANT);

        if (pending.isEmpty()) {
            return Page.empty(PageableHelper.createPageable(eventDTO));
        }

        List<String> businessCodes = pending.stream()
                .map(WorkflowTaskSummaryDTO::getBusinessKey)
                .toList();

        Specification<ProcessDetailInformationView> businessCodeSpec = (root, query, criteriaBuilder) ->
                root.get("businessCode").in(businessCodes);

        Specification<ProcessDetailInformationView> filterSpec = new FilterSpecification<>(eventDTO);

        Specification<ProcessDetailInformationView> combinedSpec = businessCodeSpec.and(filterSpec);

        Pageable pageable = PageableHelper.createPageable(eventDTO);

        Page<ProcessDetailInformationView> resultPage = processDetailInformationViewRepository.findAll(combinedSpec, pageable);

        Map<String, String> workflowTaskId = pending.stream()
                .collect(Collectors.toMap(WorkflowTaskSummaryDTO::getBusinessKey, WorkflowTaskSummaryDTO::getTaskId));

        return resultPage.map(m -> processDetailInformationViewMapper.toDTO(m, workflowTaskId));
    }

    @Override
    @Transactional(readOnly = true)
    public ProcessDetailDTO detail(String processDetailId) {
        GeneralInformationHistory generalInformationHistory = generalInformationHistoryRepository.findById(processDetailId)
                .orElseThrow(() -> new ApplicationException(ApplicationMessage.NOT_FOUND));
        return processDetailDTOMapper.toDTO(generalInformationHistory);
    }

    @Override
    public Page<ProcessDetailInformationViewDTO> available(ProcessScopeEnum scope, LazyLoadEventDTO eventDTO) {

        Specification<ProcessDetailInformationView> scopeSpec = (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("generalScope"), scope.name());

        Specification<ProcessDetailInformationView> filterSpec = new FilterSpecification<>(eventDTO);

        Specification<ProcessDetailInformationView> combinedSpec = scopeSpec.and(filterSpec);

        Pageable pageable = PageableHelper.createPageable(eventDTO);

        return processDetailInformationViewRepository.findAll(combinedSpec, pageable)
                .map(processDetailInformationViewMapper::toDto);
    }

    @Override
    public Page<GeneralInformationHistoryDTO> histories (String generalId, LazyLoadEventDTO eventDTO) {
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
    public Page<GeneralInformationDTO> availableByScope(ProcessScopeEnum scope, LazyLoadEventDTO eventDTO) {
        List<ProcessScopeConfig> processScopeConfig = processScopeConfigRepository.findByType(scope);

        Specification<GeneralInformation> specification = (root, query, criteriaBuilder) ->
                root.get("scope").in(processScopeConfig);

        Specification<GeneralInformation> filterSpec = new FilterSpecification<>(eventDTO);

        Specification<GeneralInformation> combinedSpec = specification.and(filterSpec);

        Pageable pageable = PageableHelper.createPageable(eventDTO);

        return generalInformationRepository.findAll(combinedSpec, pageable).map(generalInformationMapper::toDto);
    }

}