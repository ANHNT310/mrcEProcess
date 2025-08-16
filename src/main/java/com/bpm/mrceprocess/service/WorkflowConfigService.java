package com.bpm.mrceprocess.service;

import com.bpm.mrceprocess.common.dtos.WorkflowConfigDTO;
import com.bpm.mrceprocess.common.dtos.WorkflowConfigStatusDTO;
import com.bpm.mrceprocess.persistence.entity.WorkflowConfig;

import java.util.List;

public interface WorkflowConfigService extends CrudService<WorkflowConfig, WorkflowConfigDTO, String> {

    /**
     * Creates a new status for a workflow configuration or updates an existing one.
     *
     * @param workflowConfigId The ID of the parent WorkflowConfig.
     * @param statusDto        The DTO containing the status data. If an ID is present, it's an update; otherwise, it's a create.
     * @return The created or updated status as a DTO.
     */
    WorkflowConfigStatusDTO createOrUpdateStatus(String workflowConfigId, WorkflowConfigStatusDTO statusDto);

    /**
     * Deletes a status from a workflow configuration.
     *
     * @param workflowConfigId The ID of the parent WorkflowConfig.
     * @param statusId         The ID of the status to delete.
     */
    void deleteStatus(String workflowConfigId, String statusId);

    /**
     * Retrieves a list of all workflow statuses for a given workflow configurations.
     * @return A list of WorkflowConfigStatusDTO objects.
     */
    List<WorkflowConfigStatusDTO> getStatuses(String workflowConfigId);
}
