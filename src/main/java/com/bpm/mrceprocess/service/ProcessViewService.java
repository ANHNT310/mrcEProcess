package com.bpm.mrceprocess.service;

import com.bpm.dtos.LazyLoadEventDTO;
import com.bpm.mrceprocess.common.dtos.GeneralInformationDTO;
import com.bpm.mrceprocess.common.dtos.GeneralWorkflowViewDTO;
import com.bpm.mrceprocess.common.dtos.ProcessDetailDTO;
import com.bpm.mrceprocess.common.dtos.ProcessDetailInformationViewDTO;
import com.bpm.mrceprocess.common.enums.ProcessScopeEnum;
import org.springframework.data.domain.Page;

public interface ProcessViewService {

    Page<ProcessDetailInformationViewDTO> byUser(LazyLoadEventDTO eventDTO);

    Page<ProcessDetailInformationViewDTO> pending (LazyLoadEventDTO eventDTO);

    ProcessDetailDTO detail (String processDetailId);

    Page<ProcessDetailInformationViewDTO> available(ProcessScopeEnum scope, LazyLoadEventDTO eventDTO);
}
