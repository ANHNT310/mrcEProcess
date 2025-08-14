package com.bpm.mrceprocess.service;

import com.bpm.mrceprocess.common.dtos.*;
import com.bpm.mrceprocess.common.enums.ProcessActionSaveType;

public interface ProcessActionService {

    SaveProcessRequestDTO.Response save(ProcessActionSaveType type, SaveProcessRequestDTO.Request request);

    void deleteDraft (String historyId);

    void publicProcess (String historyId);

    void workflowCanceled(ProcessCanceledEventDTO eventDTO);

    void workflowMoveNextStep(UserTaskCreatedEventDTO eventDTO);

    SubmitDeactivateProcessDTO.Response submitDeactivate(SubmitDeactivateProcessDTO.Request request);

    void deactivateProcess(String historyId);
}
