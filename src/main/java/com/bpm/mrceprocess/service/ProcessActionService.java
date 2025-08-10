package com.bpm.mrceprocess.service;

import com.bpm.mrceprocess.common.dtos.*;

public interface ProcessActionService {

    CreateProcessRequestDTO.Response createDraft(CreateProcessRequestDTO.Request request);

    CreateProcessRequestDTO.Response createSubmit(CreateProcessRequestDTO.Request request);

    void deleteDraft (String historyId);

    UpdateProcessRequestDTO.Response updateDraft(UpdateProcessRequestDTO.Request request);

    UpdateProcessRequestDTO.Response updateSubmit(UpdateProcessRequestDTO.Request request);

    void publicProcess (String historyId);

    void workflowCanceled(ProcessCanceledEventDTO eventDTO);

    void workflowMoveNextStep(UserTaskCreatedEventDTO eventDTO);

    SubmitDeactivateProcessDTO.Response submitDeactivate(SubmitDeactivateProcessDTO.Request request);

    void deactivateProcess(String historyId);
}
