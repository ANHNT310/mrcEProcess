package com.bpm.mrceprocess.service;

import com.bpm.dtos.LazyLoadEventDTO;
import com.bpm.mrceprocess.common.dtos.*;
import com.bpm.mrceprocess.common.enums.GeneralInformationType;
import com.bpm.mrceprocess.common.enums.ProcessScopeEnum;
import org.springframework.data.domain.Page;

public interface ProcessViewService {

    Page<ViewProcessDTO> byUser(LazyLoadEventDTO eventDTO);

    Page<ProcessDetailInformationPendingDTO> pending (LazyLoadEventDTO eventDTO);

    ProcessDetailDTO detail (String processDetailId);

    Page<ProcessDetailInformationViewDTO> available(ProcessScopeEnum scope, LazyLoadEventDTO eventDTO);

    Page<GeneralInformationHistoryDTO> histories (String generalId, LazyLoadEventDTO eventDTO);

    ProcessDetailDTO availableDetail (String generalId);

    Page<ViewProcessDTO> availableByScope (GeneralInformationType type, LazyLoadEventDTO eventDTO);
}
