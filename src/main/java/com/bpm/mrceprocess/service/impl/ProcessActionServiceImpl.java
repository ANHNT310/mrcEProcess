package com.bpm.mrceprocess.service.impl;

import com.bpm.enums.ApplicationMessage;
import com.bpm.exception.ApplicationException;
import com.bpm.mrceprocess.common.consts.ApplicationConst;
import com.bpm.mrceprocess.common.dtos.CreateProcessRequestDTO;
import com.bpm.mrceprocess.common.dtos.ProcessCanceledEventDTO;
import com.bpm.mrceprocess.common.dtos.UpdateProcessRequestDTO;
import com.bpm.mrceprocess.common.dtos.UserTaskCreatedEventDTO;
import com.bpm.mrceprocess.external.WorkflowService;
import com.bpm.mrceprocess.external.payload.WorkflowStartPayloadDTO;
import com.bpm.mrceprocess.mapping.*;
import com.bpm.mrceprocess.persistence.entity.*;
import com.bpm.mrceprocess.persistence.repository.CategoryRepository;
import com.bpm.mrceprocess.persistence.repository.GeneralInformationHistoryRepository;
import com.bpm.mrceprocess.persistence.repository.GeneralInformationRepository;
import com.bpm.mrceprocess.persistence.repository.ProcessScopeConfigRepository;
import com.bpm.mrceprocess.service.ProcessActionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
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
    private final CreateProcessRequestDTOMapper createProcessRequestDTOMapper;
    private final UpdateProcessRequestDTOMapper updateProcessRequestDTOMapper;
    private final RelatedDocumentMapper relatedDocumentMapper;
    private final OriginalDocumentMapper originalDocumentMapper;
    private final DiagramDescriptionMapper diagramDescriptionMapper;
    private final TermAbbreviationMapper termAbbreviationMapper;
    private final ProcessScopeConfigRepository processScopeConfigRepository;

    @Override
    @Transactional
    public CreateProcessRequestDTO.Response createDraft(CreateProcessRequestDTO.Request request) {
        GeneralInformationHistory informationHistory = save(request);
        return new CreateProcessRequestDTO.Response(informationHistory.getGeneralInformation().getId(),
                informationHistory.getId());
    }

    @Override
    @Transactional
    public CreateProcessRequestDTO.Response createSubmit(CreateProcessRequestDTO.Request request) {
        GeneralInformationHistory informationHistory = save(request);

        WorkflowStartPayloadDTO.Request startWorkflowReq = new WorkflowStartPayloadDTO.Request(
                informationHistory.getGeneralInformation().getScope().getWorkflowName(),
                new WorkflowStartPayloadDTO.Request.Variable(informationHistory.getId())
        );
        WorkflowStartPayloadDTO.Response start = workflowService.startWorkflow(startWorkflowReq);
        informationHistory.setBusinessCode(start.businessKey());
        informationHistory = historyRepository.save(informationHistory);
        return new CreateProcessRequestDTO.Response(informationHistory.getGeneralInformation().getId(),
                informationHistory.getId());
    }

    @Override
    public void deleteDraft(String historyId) {
        GeneralInformationHistory history = historyRepository.findById(historyId)
                .orElseThrow(() -> new ApplicationException(ApplicationMessage.NOT_FOUND));

        GeneralInformation generalInformation = history.getGeneralInformation();
        if (generalInformation.getHistories().size() > 1) {
            historyRepository.delete(history);
        } else {
            generalInformationRepository.delete(generalInformation);
        }
    }

    @Transactional
    public GeneralInformationHistory save (CreateProcessRequestDTO.Request request) {

        GeneralInformationHistory informationHistory = createProcessRequestDTOMapper.toGeneralInformationHistory(request);

        Category category = categoryRepository.findById(request.information().category())
                .orElseThrow(() -> new ApplicationException(ApplicationMessage.NOT_FOUND));
        informationHistory.setCategory(category);

        ProcessScopeConfig scopeConfig = processScopeConfigRepository.findById(request.information().scope())
                .orElseThrow(() -> new ApplicationException(ApplicationMessage.NOT_FOUND));

        scopeConfig.getStatuses().stream().filter(ProcessScopeStatus::isDefaultBegin).findFirst()
                .ifPresent(informationHistory::setStatus);

        GeneralInformation generalInformation;
        int historyVersion = 1;
        if (StringUtils.isEmpty(request.information().id())) {
            int existedAvailableCode = generalInformationRepository.countByCodeIsNotNull();
            int nextValue = existedAvailableCode + 1;
            String sequencePart = String.format("%03d", nextValue);

            generalInformation = createProcessRequestDTOMapper.toGeneralInformation(request);
            generalInformation.setCode(String.format("%s-%s", ApplicationConst.GENERAL_INFORMATION_PREFIX_CODE, sequencePart));

            generalInformation = generalInformationRepository.save(generalInformation);
        } else {
            generalInformation = generalInformationRepository.findById(request.information().id())
                    .orElseThrow(() -> new ApplicationException(ApplicationMessage.NOT_FOUND));

            int existHistory = historyRepository.countByGeneralInformation(generalInformation);
            historyVersion = existHistory + 1;
        }

        informationHistory.setGeneralInformation(generalInformation);
        informationHistory.setVersion(historyVersion);
        informationHistory.setCode(String.format("%s-%s", generalInformation.getCode(), String.format("%05d", historyVersion)));

        return historyRepository.save(informationHistory);
    }

    @Override
    @Transactional
    public UpdateProcessRequestDTO.Response updateDraft(UpdateProcessRequestDTO.Request request) {
        GeneralInformationHistory updatedHistory = update(request);
        return new UpdateProcessRequestDTO.Response(updatedHistory.getGeneralInformation().getId(), updatedHistory.getId());
    }

    @Override
    @Transactional
    public UpdateProcessRequestDTO.Response updateSubmit(UpdateProcessRequestDTO.Request request) {
        GeneralInformationHistory updatedHistory = update(request);

        WorkflowStartPayloadDTO.Request startWorkflowReq = new WorkflowStartPayloadDTO.Request(
                request.information().scope().name(),
                new WorkflowStartPayloadDTO.Request.Variable(updatedHistory.getId())
        );
        WorkflowStartPayloadDTO.Response start = workflowService.startWorkflow(startWorkflowReq);

        updatedHistory.setBusinessCode(start.businessKey());
        updatedHistory = historyRepository.save(updatedHistory);

        return new UpdateProcessRequestDTO.Response(updatedHistory.getGeneralInformation().getId(), updatedHistory.getId());
    }

    @Override
    @Transactional
    public void publicProcess(String historyId) {
        GeneralInformationHistory informationHistory = historyRepository.findById(historyId)
                .orElseThrow(() -> new ApplicationException(ApplicationMessage.NOT_FOUND));


        GeneralInformation generalInformation = informationHistory.getGeneralInformation();
        ProcessScopeStatus scopeStatus = generalInformation.getScope().getStatuses().stream()
                .filter(ProcessScopeStatus::isDefaultEnd).findAny().orElse(null);
        if (scopeStatus != null) {
            informationHistory.setStatus(scopeStatus);
        }
        generalInformation.setAvailable(informationHistory);

        historyRepository.save(informationHistory);
        generalInformationRepository.save(generalInformation);
    }

    @Override
    @Transactional
    public void workflowCanceled(ProcessCanceledEventDTO eventDTO) {
        GeneralInformationHistory informationHistory = historyRepository.findByBusinessCode(eventDTO.getBusinessKey())
                .orElse(null);

        if (informationHistory == null) {
            log.error("Workflow cancel informationHistory is null");
            return;
        }

        historyRepository.save(informationHistory);
    }

    @Override
    @Transactional
    public void workflowMoveNextStep(UserTaskCreatedEventDTO eventDTO) {
        GeneralInformationHistory informationHistory = historyRepository.findByBusinessCode(eventDTO.getBusinessKey())
                .orElse(null);
        if (informationHistory == null) {
            log.error("Workflow next step informationHistory is null");
            return;
        }

        ProcessScopeConfig processScopeConfig = informationHistory.getGeneralInformation().getScope();
        if (processScopeConfig == null) {
            log.error("Workflow next step processScopeConfig is null");
            return;
        }

        //Update status
        ProcessScopeStatus processScopeStatus = processScopeConfig.getStatuses().stream()
                .filter(f -> f.getTaskName().equals(eventDTO.getTaskName()))
                .findFirst().orElse(null);
        informationHistory.setStatus(processScopeStatus);
        historyRepository.save(informationHistory);
    }

    @Transactional
    protected GeneralInformationHistory update(UpdateProcessRequestDTO.Request request) {
        // 1. Validate input
        Objects.requireNonNull(request, "Request payload cannot be null.");
        if (StringUtils.isBlank(request.information().historyId())) {
            throw new ApplicationException(ApplicationMessage.BAD_REQUEST, "History ID is required for an update.");
        }

        // 2. Fetch the entity to be updated
        GeneralInformationHistory history = historyRepository.findById(request.information().historyId())
                .orElseThrow(() -> new ApplicationException(ApplicationMessage.NOT_FOUND, "Process history not found."));

        // 3. Use the mapper to update simple, direct fields from the payload
        updateProcessRequestDTOMapper.partialUpdate(request, history);

        // 4. Manually handle complex relationships and collections
        // Update General Information (parent)
        if (history.getGeneralInformation() != null && history.getGeneralInformation().getAvailable() == null) {
            updateProcessRequestDTOMapper.partialUpdate(request, history.getGeneralInformation());
        }

        // Update Category
        if (StringUtils.isNotBlank(request.information().category())) {
            Category category = categoryRepository.findById(request.information().category())
                    .orElseThrow(() -> new ApplicationException(ApplicationMessage.NOT_FOUND, "Category not found."));
            history.setCategory(category);
        } else {
            history.setCategory(null);
        }

        synchronizeOriginalDocuments(history, request.information().originalDocuments());
        synchronizeDiagramDescriptions(history, request.diagram().descriptions());
        synchronizeTermAbbreviations(history, request.termAbbreviations());

        if (request.relatedDocument() != null) {
            RelatedDocument relatedDocument = relatedDocumentMapper.toEntity(request.relatedDocument());
            history.setRelatedDocument(relatedDocument);
            if (relatedDocument != null) {
                relatedDocument.setGeneralInformationHistory(history);
            }
        } else {
            history.setRelatedDocument(null);
        }

        return historyRepository.saveAndFlush(history);
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
