package com.bpm.mrceprocess.service.impl;

import com.bpm.mrceprocess.common.dtos.WorkflowConfigStatusDTO;
import com.bpm.mrceprocess.mapping.WorkflowConfigStatusMapper;
import com.bpm.mrceprocess.persistence.entity.WorkflowConfigStatus;
import com.bpm.mrceprocess.persistence.repository.WorkflowConfigStatusRepository;
import com.bpm.mrceprocess.service.WorkflowConfigStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkflowConfigStatusServiceImpl extends AbstractCrudService<WorkflowConfigStatus, WorkflowConfigStatusDTO, String>
        implements WorkflowConfigStatusService {

    private final WorkflowConfigStatusRepository workflowConfigStatusRepository;
    private final WorkflowConfigStatusMapper workflowConfigStatusMapper;

    @Override
    protected JpaRepository<WorkflowConfigStatus, String> getRepository() {
        return workflowConfigStatusRepository;
    }

    @Override
    protected WorkflowConfigStatus toEntity(WorkflowConfigStatusDTO dto) {
        return workflowConfigStatusMapper.toEntity(dto);
    }

    @Override
    protected WorkflowConfigStatusDTO toDTO(WorkflowConfigStatus entity) {
        return workflowConfigStatusMapper.toDto(entity);
    }

    @Override
    protected WorkflowConfigStatus toUpdateEntity(WorkflowConfigStatus entity, WorkflowConfigStatusDTO dto) {
        return workflowConfigStatusMapper.partialUpdate(dto, entity);
    }
}
