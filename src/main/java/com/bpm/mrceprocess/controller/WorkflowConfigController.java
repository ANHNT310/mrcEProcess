package com.bpm.mrceprocess.controller;

import com.bpm.dtos.BaseResponse;
import com.bpm.mrceprocess.common.dtos.WorkflowConfigDTO;
import com.bpm.mrceprocess.common.dtos.WorkflowConfigStatusDTO;
import com.bpm.mrceprocess.service.CrudService;
import com.bpm.mrceprocess.service.WorkflowConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workflow-configs")
@RequiredArgsConstructor
@Tag(name = "Workflow Configuration Management", description = "APIs for managing workflow configurations")
public class WorkflowConfigController extends AbstractCrudController<WorkflowConfigDTO, String> {
    private final WorkflowConfigService workflowConfigService;

    @Override
    protected CrudService<?, WorkflowConfigDTO, String> getService() { return workflowConfigService; }

    @Operation(summary = "Get all statuses for a workflow config", description = "Retrieves a list of all statuses for a specific workflow configuration.")
    @GetMapping("/{workflowConfigId}/statuses")
    public BaseResponse<List<WorkflowConfigStatusDTO>> getStatuses(
            @Parameter(description = "ID of the parent workflow config", required = true) @PathVariable String workflowConfigId) {
        return BaseResponse.success(workflowConfigService.getStatuses(workflowConfigId));
    }

    @Operation(summary = "Create or update a status for a workflow config", description = "Creates a new status or updates an existing one within a workflow configuration. If the DTO contains an ID, it's an update, otherwise it's a creation.")
    @PostMapping("/{workflowConfigId}/statuses")
    public BaseResponse<WorkflowConfigStatusDTO> createOrUpdateStatus(
            @Parameter(description = "ID of the parent workflow config", required = true) @PathVariable String workflowConfigId,
            @RequestBody WorkflowConfigStatusDTO statusDto) {
        return BaseResponse.success(workflowConfigService.createOrUpdateStatus(workflowConfigId, statusDto));
    }

    @Operation(summary = "Delete a status from a workflow config", description = "Deletes a specific status from a workflow configuration by its ID.")
    @DeleteMapping("/{workflowConfigId}/statuses/{statusId}")
    public BaseResponse<Void> deleteStatus(
            @Parameter(description = "ID of the parent workflow config", required = true) @PathVariable String workflowConfigId,
            @Parameter(description = "ID of the status to be deleted", required = true) @PathVariable String statusId) {
        workflowConfigService.deleteStatus(workflowConfigId, statusId);
        return BaseResponse.success(null);
    }
}