package com.bpm.mrceprocess.controller;

import com.bpm.mrceprocess.common.dtos.WorkflowConfigStatusDTO;
import com.bpm.mrceprocess.service.CrudService;
import com.bpm.mrceprocess.service.WorkflowConfigStatusService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/workflow-status")
@RequiredArgsConstructor
@Tag(name = "Workflow Configuration Status", description = "APIs for managing workflow configurations status")
public class WorkflowConfigStatusController extends AbstractCrudController<WorkflowConfigStatusDTO, String>{

    private final WorkflowConfigStatusService workflowConfigStatusService;

    @Override
    protected CrudService<?, WorkflowConfigStatusDTO, String> getService() {
        return workflowConfigStatusService;
    }
}
