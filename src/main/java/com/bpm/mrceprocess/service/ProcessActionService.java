package com.bpm.mrceprocess.service;

import com.bpm.mrceprocess.common.dtos.*;
import com.bpm.mrceprocess.common.enums.ProcessActionSaveType;

public interface ProcessActionService {

    SaveProcessResponseDTO save(ProcessActionSaveType type, SaveProcessRequestDTO request);

    DeactivateProcessResponseDTO deactivate (DeactivateProcessRequestDTO request);

    void deleteDraft (String historyId);

    void publicProcess (String historyId);

    void workflowCanceled(ProcessCanceledEventDTO eventDTO);

    void workflowMoveNextStep(UserTaskCreatedEventDTO eventDTO);

    void deactivateProcess(String historyId);
}
