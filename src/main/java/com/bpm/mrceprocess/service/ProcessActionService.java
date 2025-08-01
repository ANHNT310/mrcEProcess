package com.bpm.mrceprocess.service;

import com.bpm.mrceprocess.common.dtos.CreateProcessRequestDTO;
import com.bpm.mrceprocess.common.dtos.ProcessCanceledEventDTO;
import com.bpm.mrceprocess.common.dtos.UpdateProcessRequestDTO;

public interface ProcessActionService {

    CreateProcessRequestDTO.Response createDraft(CreateProcessRequestDTO.Request request);

    CreateProcessRequestDTO.Response createSubmit(CreateProcessRequestDTO.Request request);

    void deleteDraft (String historyId);

    UpdateProcessRequestDTO.Response updateDraft(UpdateProcessRequestDTO.Request request);

    UpdateProcessRequestDTO.Response updateSubmit(UpdateProcessRequestDTO.Request request);

    void publicProcess (String historyId);

    void workflowCanceled(ProcessCanceledEventDTO eventDTO);
}
