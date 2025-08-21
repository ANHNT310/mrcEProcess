package com.bpm.mrceprocess.controller;

import com.bpm.dtos.BaseResponse;
import com.bpm.dtos.SelectItem;
import com.bpm.mrceprocess.service.MasterDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/master-data")
@RequiredArgsConstructor
@Tag(name = "Master Data", description = "APIs for retrieving master data and selection lists")
public class MasterDataController {

    private final MasterDataService masterDataService;

    @GetMapping("/process-scopes")
    @Operation(summary = "Get Process Scopes", description = "Retrieves a list of available process scopes.")
    public BaseResponse<List<SelectItem>> getProcessScopes() {
        return BaseResponse.success(masterDataService.processScope());
    }

    @GetMapping("/categories")
    @Operation(summary = "Get Categories", description = "Retrieves a list of all process categories.")
    public BaseResponse<List<SelectItem>> getCategories() {
        return BaseResponse.success(masterDataService.SelectCategory());
    }

    @GetMapping("/effective-types")
    @Operation(summary = "Get Effective Types", description = "Retrieves a list of available effective types.")
    public BaseResponse<List<SelectItem>> getEffectiveTypes() {
        return BaseResponse.success(masterDataService.effectiveType());
    }

    @GetMapping("/document-statuses")
    @Operation(summary = "Get Document Statuses", description = "Retrieves a list of available document statuses.")
    public BaseResponse<List<SelectItem>> getDocumentStatuses() {
        return BaseResponse.success(masterDataService.documentStatus());
    }

    @GetMapping("/document-types")
    @Operation(summary = "Get Document Types", description = "Retrieves a list of all document types.")
    public BaseResponse<List<SelectItem>> getDocumentTypes() {
        return BaseResponse.success(masterDataService.documentType());
    }

    @GetMapping("/authorities")
    @Operation(summary = "Get Authorities", description = "Retrieves a list of all authorities/roles.")
    public BaseResponse<List<SelectItem>> getAuthorities() {
        return BaseResponse.success(masterDataService.authorities());
    }

    @GetMapping("/processes")
    @Operation(summary = "Get Processes", description = "Retrieves a list of all available processes.")
    public BaseResponse<List<SelectItem>> getProcesses() {
        return BaseResponse.success(masterDataService.processes());
    }

    @GetMapping("/workflow")
    @Operation(summary = "Get Workflow", description = "Retrieves a list of all available processes.")
    public BaseResponse<List<SelectItem>> getWorkflow() {
        return BaseResponse.success(masterDataService.workflow());
    }
}