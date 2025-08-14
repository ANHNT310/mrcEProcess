package com.bpm.mrceprocess.service.impl;

import com.bpm.enums.ApplicationMessage;
import com.bpm.exception.ApplicationException;
import com.bpm.mrceprocess.common.consts.ApplicationConst;
import com.bpm.mrceprocess.common.dtos.*;
import com.bpm.mrceprocess.common.enums.GeneralInformationType;
import com.bpm.mrceprocess.common.enums.ProcessActionSaveType;
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
    private final CategoryRepository categoryRepository;
    private final WorkflowService workflowService;
    private final RelatedDocumentMapper relatedDocumentMapper;
    private final OriginalDocumentMapper originalDocumentMapper;
    private final DiagramDescriptionMapper diagramDescriptionMapper;
    private final TermAbbreviationMapper termAbbreviationMapper;
    private final ProcessScopeConfigRepository processScopeConfigRepository;
    private final GeneralInformationWorkflowRepository generalInformationWorkflowRepository;
    private final SaveProcessRequestDTOMapper saveProcessRequestDTOMapper;

    @Override
    @Transactional
    public SaveProcessRequestDTO.Response save(ProcessActionSaveType type, SaveProcessRequestDTO.Request request) {

        GeneralInformation generalInformation;
        if (StringUtils.isEmpty(request.general().code())) {

            int lastValue = generalInformationRepository.countAllByCodeIsNotNull();
            int nextValue = lastValue + 1;
            String sequencePart = String.format("%03d", nextValue);

            generalInformation = GeneralInformation.builder()
                    .code(String.format("%s-%s", ApplicationConst.GENERAL_INFORMATION_PREFIX_CODE, sequencePart))
                    .type(GeneralInformationType.valueOf(request.general().type()))
                    .build();
            generalInformation = generalInformationRepository.save(generalInformation);
        } else {
            generalInformation = generalInformationRepository.findByCode(request.general().code())
                    .orElseThrow(() -> new ApplicationException(ApplicationMessage.NOT_FOUND, "General information not found"));
        }

        GeneralInformationHistory informationHistory;
        if (StringUtils.isEmpty(request.information().id())) {
            informationHistory = saveProcessRequestDTOMapper.fromDTO(request);
        } else {
            informationHistory = historyRepository.findById(request.information().id())
                    .orElseThrow(() -> new ApplicationException(ApplicationMessage.NOT_FOUND,"History not found"));
            saveProcessRequestDTOMapper.updateFromDTO(informationHistory, request);
        }

        int countByGeneral = historyRepository.countByGeneralInformation(generalInformation);
        informationHistory.setVersion(countByGeneral + 1);
        informationHistory.setGeneralInformation(generalInformation);

        if (ProcessActionSaveType.submit.equals(type)) {
            Map<String, Object> createPayload = new HashMap<>();
            createPayload.put(ApplicationConst.E_PROCESS_ID_VARIABLE_FIELD, informationHistory.getId());

            GeneralInformationType informationType = informationHistory.getGeneralInformation().getType();

            GeneralInformationWorkflow generalInformationWorkflow = generalInformationWorkflowRepository.findByType(informationType)
                    .orElseThrow(() -> new ApplicationException(ApplicationMessage.NOT_FOUND, "Workflow not found"));

            WorkflowStartPayloadResDTO started = workflowService.startWorkflow(generalInformationWorkflow.getWorkflowName(), createPayload);

            GeneralInformationHistoryTicket ticket = new GeneralInformationHistoryTicket();
            ticket.setInformationHistory(informationHistory);
            ticket.setInformationWorkflow(generalInformationWorkflow);
            ticket.setBusinessCode(started.getBusinessKey());

            informationHistory.addGeneralInformationHistoryWorkflow(ticket);
            informationHistory.setDraft(false);
        }

        informationHistory = historyRepository.save(informationHistory);

        return new SaveProcessRequestDTO.Response(generalInformation.getCode(), informationHistory.getId());
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
//        GeneralInformationHistory informationHistory = historyRepository.findByBusinessCode(eventDTO.getBusinessKey())
//                .orElse(null);
//
//        if (informationHistory == null) {
//            log.error("Workflow cancel informationHistory is null");
//            return;
//        }
//
//        historyRepository.save(informationHistory);
    }

    @Override
    @Transactional
    public void workflowMoveNextStep(UserTaskCreatedEventDTO eventDTO) {
//        GeneralInformationHistory informationHistory = historyRepository.findByBusinessCode(eventDTO.getBusinessKey())
//                .orElse(null);
//        if (informationHistory == null) {
//            log.error("Workflow next step informationHistory is null");
//            return;
//        }
//        historyRepository.save(informationHistory);
    }

    @Override
    public SubmitDeactivateProcessDTO.Response submitDeactivate(SubmitDeactivateProcessDTO.Request request) {
        GeneralInformation generalInformation = generalInformationRepository.findById(request.generalId())
                .orElseThrow(() -> new ApplicationException(ApplicationMessage.NOT_FOUND));

        if (generalInformation.getAvailable() == null) {
            return null;
        }

        Map<String, Object> variables = new HashMap<>();
        variables.put(ApplicationConst.E_PROCESS_ID_VARIABLE_FIELD, generalInformation.getAvailable().getId());

        WorkflowStartPayloadResDTO startRes = workflowService.startWorkflow(ApplicationConst.WORKFLOW_DEACTIVATE_NAME, variables);

        return new SubmitDeactivateProcessDTO.Response(generalInformation.getAvailable().getId(), startRes.getBusinessKey());
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

    private void synchronizeOriginalDocuments(GeneralInformationHistory history,
                                              List<UpdateProcessRequestDTO.Request.Information.OriginalDocument> documentDTOs) {
        Set<OriginalDocument> existingDocuments = history.getOriginalDocuments();
        if (documentDTOs == null || documentDTOs.isEmpty()) {
            existingDocuments.clear();
            return;
        }

        Map<String, OriginalDocument> existingDocsMap = existingDocuments.stream()
                .collect(Collectors.toMap(OriginalDocument::getId, Function.identity()));
        Map<String, UpdateProcessRequestDTO.Request.Information.OriginalDocument> dtoMap = documentDTOs.stream()
                .filter(dto -> StringUtils.isNotBlank(dto.id()))
                .collect(Collectors.toMap(UpdateProcessRequestDTO.Request.Information.OriginalDocument::id, Function.identity()));

        existingDocuments.removeIf(doc -> !dtoMap.containsKey(doc.getId()));

        for (UpdateProcessRequestDTO.Request.Information.OriginalDocument dto : documentDTOs) {
            if (StringUtils.isBlank(dto.id())) {
                OriginalDocument newDoc = originalDocumentMapper.toEntity(dto);
                history.addOriginalDocument(newDoc);
            } else {
                OriginalDocument existingDoc = existingDocsMap.get(dto.id());
                if (existingDoc != null) {
                    originalDocumentMapper.partialUpdate(dto, existingDoc);
                }
            }
        }
    }

    private void synchronizeDiagramDescriptions(GeneralInformationHistory history,
                                                List<UpdateProcessRequestDTO.Request.Diagram.Description> descriptionDTOs) {
        Set<DiagramDescription> existingDescriptions = history.getDiagramDescriptions();
        if (descriptionDTOs == null || descriptionDTOs.isEmpty()) {
            existingDescriptions.clear();
            return;
        }

        Map<String, DiagramDescription> existingDescMap = existingDescriptions.stream()
                .filter(desc -> desc.getId() != null) // FIX: Prevent exception from items with null ID
                .collect(Collectors.toMap(DiagramDescription::getId, Function.identity()));
        Map<String, UpdateProcessRequestDTO.Request.Diagram.Description> dtoMap = descriptionDTOs.stream()
                .filter(dto -> StringUtils.isNotBlank(dto.id()))
                .collect(Collectors.toMap(UpdateProcessRequestDTO.Request.Diagram.Description::id, Function.identity()));

        existingDescriptions.removeIf(desc -> !dtoMap.containsKey(desc.getId()));

        for (UpdateProcessRequestDTO.Request.Diagram.Description dto : descriptionDTOs) {
            if (StringUtils.isBlank(dto.id())) {
                DiagramDescription newDesc = diagramDescriptionMapper.toEntity(dto);
                history.addDiagramDescription(newDesc);
            } else {
                DiagramDescription existingDesc = existingDescMap.get(dto.id());
                if (existingDesc != null) {
                    diagramDescriptionMapper.partialUpdate(dto, existingDesc);
                }
            }
        }
    }

    private void synchronizeTermAbbreviations(GeneralInformationHistory history, List<UpdateProcessRequestDTO.Request.TermAbbreviation> termDTOs) {
        Set<TermAbbreviation> existingTerms = history.getTermAbbreviations();
        if (termDTOs == null || termDTOs.isEmpty()) {
            existingTerms.clear();
            return;
        }

        Map<String, TermAbbreviation> existingTermMap = existingTerms.stream()
                .collect(Collectors.toMap(TermAbbreviation::getId, Function.identity()));
        Map<String, UpdateProcessRequestDTO.Request.TermAbbreviation> dtoMap = termDTOs.stream()
                .filter(dto -> StringUtils.isNotBlank(dto.id()))
                .collect(Collectors.toMap(UpdateProcessRequestDTO.Request.TermAbbreviation::id, Function.identity()));

        existingTerms.removeIf(term -> !dtoMap.containsKey(term.getId()));

        for (UpdateProcessRequestDTO.Request.TermAbbreviation dto : termDTOs) {
            if (StringUtils.isBlank(dto.id())) {
                TermAbbreviation newTerm = termAbbreviationMapper.toEntity(dto);
                history.addTermAbbreviation(newTerm);
            } else {
                TermAbbreviation existingTerm = existingTermMap.get(dto.id());
                if (existingTerm != null) {
                    termAbbreviationMapper.partialUpdate(dto, existingTerm);
                }
            }
        }
    }
}
