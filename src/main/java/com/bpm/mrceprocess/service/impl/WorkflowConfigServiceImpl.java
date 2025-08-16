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
    private final WorkflowConfigStatusMapper workflowConfigStatusMapper;

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

    @Override
    @Transactional
    public WorkflowConfigStatusDTO createOrUpdateStatus(String workflowConfigId, WorkflowConfigStatusDTO statusDto) {
        WorkflowConfig workflowConfig = workflowConfigRepository.findById(workflowConfigId)
                .orElseThrow(() -> new ApplicationException(ApplicationMessage.NOT_FOUND, "WorkflowConfig not found with id: " + workflowConfigId));

        WorkflowConfigStatus statusEntity;
        if (StringUtils.isNotBlank(statusDto.getId())) {
            // Update logic
            statusEntity = workflowConfig.getStatuses().stream()
                    .filter(s -> s.getId().equals(statusDto.getId()))
                    .findFirst()
                    .orElseThrow(() -> new ApplicationException(ApplicationMessage.NOT_FOUND, "Status with id " + statusDto.getId() + " not found in WorkflowConfig " + workflowConfigId));
            workflowConfigStatusMapper.partialUpdate(statusDto, statusEntity);
        } else {
            // Create logic
            statusEntity = workflowConfigStatusMapper.toEntity(statusDto);
            workflowConfig.addStatus(statusEntity);
        }

        // Ensure uniqueness of default flags
        handleDefaultFlags(workflowConfig, statusEntity);

        // The save on the parent will cascade to the child (create or update)
        workflowConfigRepository.save(workflowConfig);

        // Return the DTO of the persisted entity, which now has an ID
        return workflowConfigStatusMapper.toDto(statusEntity);
    }

    @Override
    @Transactional
    public void deleteStatus(String workflowConfigId, String statusId) {
        WorkflowConfig workflowConfig = workflowConfigRepository.findById(workflowConfigId)
                .orElseThrow(() -> new ApplicationException(ApplicationMessage.NOT_FOUND, "WorkflowConfig not found with id: " + workflowConfigId));

        WorkflowConfigStatus statusToRemove = workflowConfig.getStatuses().stream()
                .filter(s -> s.getId().equals(statusId))
                .findFirst()
                .orElseThrow(() -> new ApplicationException(ApplicationMessage.NOT_FOUND, "Status with id " + statusId + " not found in WorkflowConfig " + workflowConfigId));

        // The removeStatus helper and orphanRemoval=true will handle the deletion
        workflowConfig.removeStatus(statusToRemove);
    }

    @Override
    public List<WorkflowConfigStatusDTO> getStatuses(String workflowConfigId) {
        WorkflowConfig workflowConfig = workflowConfigRepository.findById(workflowConfigId)
                .orElseThrow(() -> new ApplicationException(ApplicationMessage.NOT_FOUND, "WorkflowConfig not found with id: " + workflowConfigId));

        return workflowConfig.getStatuses().stream()
                .map(workflowConfigStatusMapper::toDto)
                .toList();
    }

    /**
     * Ensures that if the given status is marked as a default (begin or end),
     * any other status in the set is unmarked.
     */
    private void handleDefaultFlags(WorkflowConfig parent, WorkflowConfigStatus currentStatus) {
        if (currentStatus.isDefaultBegin()) {
            parent.getStatuses().stream()
                    .filter(s -> !s.equals(currentStatus) && s.isDefaultBegin())
                    .forEach(s -> s.setDefaultBegin(false));
        }
        if (currentStatus.isDefaultEnd()) {
            parent.getStatuses().stream()
                    .filter(s -> !s.equals(currentStatus) && s.isDefaultEnd())
                    .forEach(s -> s.setDefaultEnd(false));
        }
    }


}
