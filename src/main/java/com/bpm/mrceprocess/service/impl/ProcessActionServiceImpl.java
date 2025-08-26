package com.bpm.mrceprocess.service.impl;

import com.bpm.enums.ApplicationMessage;
import com.bpm.exception.ApplicationException;
import com.bpm.mrceprocess.common.consts.ApplicationConst;
import com.bpm.mrceprocess.common.dtos.*;
import com.bpm.mrceprocess.common.enums.GeneralInformationType;
import com.bpm.mrceprocess.common.enums.ProcessActionSaveType;
import com.bpm.mrceprocess.common.enums.WorkflowConfigScope;
import com.bpm.mrceprocess.external.WorkflowService;
import com.bpm.mrceprocess.external.payload.WorkflowStartPayloadResDTO;
import com.bpm.mrceprocess.mapping.*;
import com.bpm.mrceprocess.persistence.entity.*;
import com.bpm.mrceprocess.persistence.repository.*;
import com.bpm.mrceprocess.service.ProcessActionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProcessActionServiceImpl implements ProcessActionService {

    private final GeneralInformationHistoryRepository historyRepository;
    private final GeneralInformationRepository generalInformationRepository;
    private final WorkflowService workflowService;
    private final OriginalDocumentMapper originalDocumentMapper;
    private final DiagramDescriptionMapper diagramDescriptionMapper;
    private final TermAbbreviationMapper termAbbreviationMapper;
    private final WorkflowConfigRepository workflowConfigRepository;
    private final SaveProcessRequestDTOMapper saveProcessRequestDTOMapper;
    private final GeneralInformationHistoryTicketRepository informationHistoryTicketRepository;
    private final WorkflowConfigStatusRepository workflowConfigStatusRepository;

    @Override
    @Transactional
    public SaveProcessResponseDTO save(ProcessActionSaveType type, SaveProcessRequestDTO request) {

        WorkflowConfig informationWorkflow = workflowConfigRepository.findById(request.getInformation().getWorkflow())
                .orElseThrow(() -> new ApplicationException(ApplicationMessage.NOT_FOUND, "Information Workflow not found."));

        GeneralInformationType currentType = informationWorkflow.getType();

        GeneralInformation generalInformation;
        if (StringUtils.isEmpty(request.getGeneral().getCode())) {
            int lastValue = generalInformationRepository.countAllByCodeIsNotNull();
            int nextValue = lastValue + 1;
            String sequencePart = String.format("%03d", nextValue);

            generalInformation = GeneralInformation.builder()
                    .code(String.format("%s-%s", ApplicationConst.GENERAL_INFORMATION_PREFIX_CODE, sequencePart))
                    .type(currentType)
                    .build();
            generalInformation = generalInformationRepository.save(generalInformation);
        } else {
            generalInformation = generalInformationRepository.findByCode(request.getGeneral().getCode())
                    .orElseThrow(() -> new ApplicationException(ApplicationMessage.NOT_FOUND, "General information not found"));
        }

        GeneralInformationHistory informationHistory;
        if (StringUtils.isEmpty(request.getInformation().getId())) {
            informationHistory = saveProcessRequestDTOMapper.fromDTO(request);
        } else {
            informationHistory = historyRepository.findById(request.getInformation().getId())
                    .orElseThrow(() -> new ApplicationException(ApplicationMessage.NOT_FOUND,"History not found"));
            saveProcessRequestDTOMapper.updateFromDTO(informationHistory, request);
        }

        int countByGeneral = historyRepository.countByGeneralInformation(generalInformation);
        informationHistory.setVersion(countByGeneral + 1);
        informationHistory.setGeneralInformation(generalInformation);

        informationHistory = historyRepository.saveAndFlush(informationHistory);

        if (ProcessActionSaveType.submit.equals(type)) {
            Map<String, Object> createPayload = new HashMap<>();
            createPayload.put(ApplicationConst.E_PROCESS_ID_VARIABLE_FIELD, informationHistory.getId());

            WorkflowStartPayloadResDTO started = workflowService.startWorkflow(informationWorkflow.getWorkflowName(), createPayload);

            GeneralInformationHistoryTicket ticket = new GeneralInformationHistoryTicket();
            ticket.setInformationHistory(informationHistory);
            ticket.setBusinessCode(started.getBusinessKey());

            informationHistory.addGeneralInformationHistoryWorkflow(ticket);
            informationHistory.setDraft(false);
            informationHistory = historyRepository.save(informationHistory);
        }

        return new SaveProcessResponseDTO(generalInformation.getCode(), informationHistory.getId());
    }

    @Override
    public DeactivateProcessResponseDTO deactivate(DeactivateProcessRequestDTO request) {
        GeneralInformation generalInformation = generalInformationRepository.findById(request.getGeneralId())
                        .orElseThrow(() -> new ApplicationException(ApplicationMessage.NOT_FOUND));

        GeneralInformationHistory availableHistory = generalInformation.getAvailable();

        List<WorkflowConfig> workflowConfigs = workflowConfigRepository.findByScope(WorkflowConfigScope.DEACTIVATE);

        if (workflowConfigs.isEmpty()) {
            throw new ApplicationException(ApplicationMessage.NOT_FOUND, "Workflow not found");
        }

        String workflowName = null;
        WorkflowConfig workflowConfigAll = workflowConfigs.stream().filter(m -> m.getType().equals(GeneralInformationType.ALL))
                .findFirst().orElse(null);
        if (workflowConfigAll != null) {
            workflowName = workflowConfigAll.getWorkflowName();
        } else {
            WorkflowConfig workflowConfigByType = workflowConfigs.stream()
                    .filter(m -> m.getType().equals(availableHistory.getWorkflow().getType()))
                    .findFirst().orElse(null);
            if (workflowConfigByType == null) {
                throw new ApplicationException(ApplicationMessage.NOT_FOUND, "Workflow not found");
            } else {
                workflowName = workflowConfigByType.getWorkflowName();
            }
        }

        Map<String, Object> variables = new HashMap<>();
        variables.put(ApplicationConst.E_PROCESS_ID_VARIABLE_FIELD, generalInformation.getAvailable().getId());
        variables.put(ApplicationConst.E_PROCESS_SCOPE_VARIABLE_FIELD, availableHistory.getWorkflow().getType());

        WorkflowStartPayloadResDTO started = workflowService.startWorkflow(workflowName, variables);
        GeneralInformationHistoryTicket ticket = new GeneralInformationHistoryTicket();
        ticket.setInformationHistory(availableHistory);
        ticket.setBusinessCode(started.getBusinessKey());

        availableHistory.addGeneralInformationHistoryWorkflow(ticket);
        historyRepository.save(availableHistory);

        return new DeactivateProcessResponseDTO(generalInformation.getAvailable().getId(), started.getBusinessKey());
    }

    @Override
    public void deleteDraft(String historyId) {
        GeneralInformationHistory history = historyRepository.findById(historyId)
                .orElseThrow(() -> new ApplicationException(ApplicationMessage.NOT_FOUND));

        if (!history.isDraft()) {
            throw new ApplicationException(ApplicationMessage.BAD_REQUEST, "This is not a draft process");
        }

        GeneralInformation generalInformation = history.getGeneralInformation();
        if (generalInformation.getHistories().size() > 1) {
            historyRepository.delete(history);
        } else {
            generalInformationRepository.delete(generalInformation);
        }
    }

    @Override
    @Transactional
    public void publicProcess(String historyId) {
        GeneralInformationHistory informationHistory = historyRepository.findById(historyId)
                .orElseThrow(() -> new ApplicationException(ApplicationMessage.NOT_FOUND));


        GeneralInformation generalInformation = informationHistory.getGeneralInformation();
        generalInformation.setAvailable(informationHistory);

        historyRepository.save(informationHistory);
        generalInformationRepository.save(generalInformation);
    }

    @Override
    @Transactional
    public void workflowCanceled(ProcessCanceledEventDTO eventDTO) {
        GeneralInformationHistoryTicket ticket = informationHistoryTicketRepository.findByBusinessCode(eventDTO.getBusinessKey())
                .orElse(null);
        if (ticket == null) {
            log.error("Workflow canceled informationHistory is null");
            return;
        }
        ticket.setCompleted(true);
        informationHistoryTicketRepository.save(ticket);
    }

    @Override
    @Transactional
    public void workflowMoveNextStep(UserTaskCreatedEventDTO eventDTO) {
        GeneralInformationHistoryTicket ticket = informationHistoryTicketRepository.findByBusinessCode(eventDTO.getBusinessKey())
                .orElse(null);
        if (ticket == null) {
            log.error("Workflow next step informationHistory is null");
            return;
        }

        WorkflowConfigStatus updateStatus = workflowConfigStatusRepository.findByTaskName(eventDTO.getTaskName())
                .orElse(null);

        GeneralInformationHistory informationHistory = ticket.getInformationHistory();
        informationHistory.setStatus(updateStatus == null ? eventDTO.getTaskName() : updateStatus.getName());

        historyRepository.save(informationHistory);
    }

    @Override
    @Transactional
    public void deactivateProcess(String historyId) {
        GeneralInformationHistory informationHistory = historyRepository.findById(historyId)
                .orElseThrow(() -> new ApplicationException(ApplicationMessage.NOT_FOUND));

        informationHistory.setDeactivate(true);
        historyRepository.save(informationHistory);

        generalInformationRepository.findByAvailable(informationHistory).ifPresent(generalInformation -> {
            generalInformation.setAvailable(null);
            generalInformationRepository.save(generalInformation);
        });
    }
}
