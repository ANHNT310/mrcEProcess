package com.bpm.mrceprocess.service;

import com.bpm.mrceprocess.common.dtos.*;

public interface ProcessActionService {

    SaveProcessRequestDTO.Response save(boolean submit, SaveProcessRequestDTO.Request request);

    void deleteDraft (String historyId);

    void publicProcess (String historyId);

    void workflowCanceled(ProcessCanceledEventDTO eventDTO);

    void workflowMoveNextStep(UserTaskCreatedEventDTO eventDTO);

    SubmitDeactivateProcessDTO.Response submitDeactivate(SubmitDeactivateProcessDTO.Request request);

    void deactivateProcess(String historyId);
}
