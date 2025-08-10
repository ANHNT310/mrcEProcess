package com.bpm.mrceprocess.service.impl;

import com.bpm.dtos.LazyLoadEventDTO;
import com.bpm.enums.ApplicationMessage;
import com.bpm.exception.ApplicationException;
import com.bpm.mrceprocess.common.consts.ApplicationConst;
import com.bpm.mrceprocess.common.dtos.*;
import com.bpm.mrceprocess.common.enums.ProcessScopeEnum;
import com.bpm.mrceprocess.external.WorkflowService;
import com.bpm.mrceprocess.external.payload.WorkflowTaskSummaryDTO;
import com.bpm.mrceprocess.mapping.*;
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
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    private final ProcessDetailInformationPendingDTOMapper processDetailInformationPendingDTOMapper;

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
    public Page<ProcessDetailInformationPendingDTO> pending(LazyLoadEventDTO eventDTO) {
        List<WorkflowTaskSummaryDTO> pending = workflowService.pending(ApplicationConst.WORKFLOW_TENANT);

        if (pending.isEmpty()) {
            return Page.empty(PageableHelper.createPageable(eventDTO));
        }

        List<String> eProcessIds = pending.stream()
                .map(m -> m.getVariables().getOrDefault("eProcessId", null).toString())
                .toList();
        Map<String, ProcessDetailInformationView> informationViews = processDetailInformationViewRepository.findByHistoryIdIn(eProcessIds)
                .stream().collect(Collectors.toMap(ProcessDetailInformationView::getHistoryId, m -> m));

        List<ProcessDetailInformationPendingDTO> informationPendingDTOS = new ArrayList<>();
        pending.forEach(m -> {
            String eProcessId = m.getVariables().getOrDefault("eProcessId", null).toString();
            if (!StringUtils.isEmpty(eProcessId)) {
                ProcessDetailInformationView view = informationViews.get(eProcessId);
                if (view == null) {
                    return;
                }
                informationPendingDTOS.add(processDetailInformationPendingDTOMapper.toDTO(view, m));
            }
        });

        return PageableHelper.createPageFromList(informationPendingDTOS, eventDTO);
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