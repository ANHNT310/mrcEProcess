package com.bpm.mrceprocess.controller;

import com.bpm.mrceprocess.common.dtos.ProcessScopeConfigDTO;
import com.bpm.mrceprocess.service.CrudService;
import com.bpm.mrceprocess.service.ProcessScopeConfigService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/scope")
@RequiredArgsConstructor
@Tag(name = "Scope Management", description = "APIs for managing scopes")
public class ScopeController extends AbstractCrudController<ProcessScopeConfigDTO, String> {

    private final ProcessScopeConfigService processScopeConfigService;

    @Override
    protected CrudService<?, ProcessScopeConfigDTO, String> getService() {
        return processScopeConfigService;
    }
}
