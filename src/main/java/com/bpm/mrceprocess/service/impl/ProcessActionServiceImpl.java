package com.bpm.mrceprocess.service.impl;

import com.bpm.enums.ApplicationMessage;
import com.bpm.exception.ApplicationException;
import com.bpm.mrceprocess.common.consts.ApplicationConst;
import com.bpm.mrceprocess.common.dtos.*;
import com.bpm.mrceprocess.common.enums.GeneralInformationType;
import com.bpm.mrceprocess.common.enums.ProcessActionSaveType;
import com.bpm.mrceprocess.common.enums.ProcessInformationHistStage;
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
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.CollectionUtils;

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
    private final PlatformTransactionManager transactionManager;

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

        final GeneralInformationHistory finalInformationHistory = informationHistory;

        if (finalInformationHistory.getTermAbbreviations() != null) {
            finalInformationHistory.getTermAbbreviations()
                    .forEach(term -> term.setGeneralInformationHistory(finalInformationHistory));
        }

        int countByGeneral = historyRepository.countByGeneralInformation(generalInformation);
        if (request.getDiagram() != null && !CollectionUtils.isEmpty(request.getDiagram().getDescriptions())) {
            List<DiagramDescription> diagrams = request.getDiagram().getDescriptions().stream().map(saveProcessRequestDTOMapper::fromDTO).toList();
            diagrams.forEach(informationHistory::addDiagramDescription);
        }
        informationHistory.setVersion(countByGeneral + 1);
        informationHistory.setGeneralInformation(generalInformation);
        informationHistory.setDraft(true);
        informationHistory.setStatus(ProcessInformationHistStage.DRAFT.name());

        informationHistory = historyRepository.save(informationHistory);

        if (ProcessActionSaveType.submit.equals(type)) {
            // 1. Tạo ticket và lưu ngay trước
            GeneralInformationHistoryTicket ticket = new GeneralInformationHistoryTicket();
            ticket.setInformationHistory(informationHistory);
            ticket = informationHistoryTicketRepository.saveAndFlush(ticket);  // lưu và flush ngay

            // 2. Bắt đầu workflow với businessKey là ticket ID hoặc chờ lấy từ workflow response
            Map<String, Object> createPayload = new HashMap<>();
            createPayload.put(ApplicationConst.E_PROCESS_ID_VARIABLE_FIELD, informationHistory.getId());
            log.info("Start workflow:" + createPayload);

            WorkflowStartPayloadResDTO started = workflowService.startWorkflow(informationWorkflow.getWorkflowName(), createPayload);
            log.info("Bussiness key" + started.getBusinessKey());

            // 3. Cập nhật businessCode cho ticket và lưu tiếp
            ticket.setBusinessCode(started.getBusinessKey());
            informationHistoryTicketRepository.saveAndFlush(ticket);

            // 4. Cập nhật trạng thái draft và liên kết ticket cho informationHistory
            informationHistory.addGeneralInformationHistoryWorkflow(ticket);
            informationHistory.setDraft(false);
            historyRepository.save(informationHistory);
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
        informationHistory.setStatus("VALID");

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
    public void workflowMoveNextStep(UserTaskCreatedEventDTO eventDTO) {
        final int maxRetries = 5;
        final long delayMs = 500; // Delay 500ms giữa các lần retry

        for (int attempt = 1; attempt <= maxRetries; attempt++) {
            Boolean success = getaBoolean(eventDTO, attempt);

            if (Boolean.TRUE.equals(success)) {
                return; // Thực thi thành công, thoát method
            } else {
                try {
                    Thread.sleep(delayMs);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    log.error("Interrupted while waiting to retry workflowMoveNextStep", e);
                    return; // Không tiếp tục retry nếu bị interrupt
                }
            }
        }

        // Nếu sau tất cả các lần retry vẫn không tìm thấy ticket
        log.error("Failed to find Workflow next step informationHistoryTicket after {} attempts, businessKey={}", maxRetries, eventDTO.getBusinessKey());
    }

    private Boolean getaBoolean(UserTaskCreatedEventDTO eventDTO, int attempt) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);

        int finalAttempt = attempt;
        Boolean success = transactionTemplate.execute(status -> {
            GeneralInformationHistoryTicket ticket = informationHistoryTicketRepository.findByBusinessCode(eventDTO.getBusinessKey())
                    .orElse(null);
            if (ticket == null) {
                log.warn("Attempt {}: Workflow next step informationHistoryTicket is null, businessKey={}", finalAttempt, eventDTO.getBusinessKey());
                return false; // retry
            }

            WorkflowConfigStatus updateStatus = workflowConfigStatusRepository.findByTaskName(eventDTO.getTaskName())
                    .orElse(null);

            GeneralInformationHistory informationHistory = ticket.getInformationHistory();
            informationHistory.setStatus(updateStatus == null ? eventDTO.getTaskName() : updateStatus.getName());

            historyRepository.save(informationHistory);
            return true; // success, không cần retry nữa
        });
        return success;
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
