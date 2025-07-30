package com.bpm.mrceprocess.controller;

import com.bpm.mrceprocess.common.dtos.ProcessStatusMappingDTO;
import com.bpm.mrceprocess.service.CrudService;
import com.bpm.mrceprocess.service.ProcessStatusMappingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/process/status-mappings")
@RequiredArgsConstructor
@Tag(name = "Process Status Mapping Admin", description = "APIs for managing business status mappings for process tasks")
public class ProcessStatusMappingController extends AbstractCrudController<ProcessStatusMappingDTO, String> {

    private final ProcessStatusMappingService service;

    @Override
    protected CrudService<?, ProcessStatusMappingDTO, String> getService() {
        return service;
    }
}
