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
}