package com.bpm.mrceprocess.service.impl;

import com.bpm.enums.ApplicationMessage;
import com.bpm.exception.ApplicationException;
import com.bpm.mrceprocess.common.dtos.WorkflowConfigDTO;
import com.bpm.mrceprocess.common.dtos.WorkflowConfigStatusDTO;
import com.bpm.mrceprocess.mapping.WorkflowConfigMapper;
import com.bpm.mrceprocess.mapping.WorkflowConfigStatusMapper;
import com.bpm.mrceprocess.persistence.entity.WorkflowConfig;
import com.bpm.mrceprocess.persistence.entity.WorkflowConfigStatus;
import com.bpm.mrceprocess.persistence.repository.WorkflowConfigRepository;
import com.bpm.mrceprocess.service.WorkflowConfigService;
import com.bpm.service.AbstractCrudService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkflowConfigServiceImpl extends AbstractCrudService<WorkflowConfig, WorkflowConfigDTO, String> implements WorkflowConfigService {

    private final WorkflowConfigRepository workflowConfigRepository;
    private final WorkflowConfigMapper workflowConfigMapper;

    @Override
    protected JpaRepository<WorkflowConfig, String> getRepository() {
        return workflowConfigRepository;
    }

    @Override
    protected WorkflowConfig toEntity(WorkflowConfigDTO dto) {
        return workflowConfigMapper.toEntity(dto);
    }

    @Override
    protected WorkflowConfigDTO toDTO(WorkflowConfig entity) {
        return workflowConfigMapper.toDto(entity);
    }

    @Override
    protected WorkflowConfig toUpdateEntity(WorkflowConfig entity, WorkflowConfigDTO dto) {
        return workflowConfigMapper.partialUpdate(dto, entity);
    }
}
