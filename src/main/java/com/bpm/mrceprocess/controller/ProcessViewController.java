package com.bpm.mrceprocess.controller;

import com.bpm.dtos.BaseResponse;
import com.bpm.dtos.LazyLoadEventDTO;
import com.bpm.dtos.PageResponse;
import com.bpm.mrceprocess.common.dtos.ProcessDetailDTO;
import com.bpm.mrceprocess.common.dtos.ProcessDetailInformationViewDTO;
import com.bpm.mrceprocess.common.enums.ProcessScopeEnum;
import com.bpm.mrceprocess.service.ProcessViewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/process/view")
@RequiredArgsConstructor
@Tag(name = "Process View", description = "APIs for querying and viewing process information")
public class ProcessViewController {

    private final ProcessViewService processViewService;

    @PostMapping("/by-user")
    @Operation(summary = "Get processes by current user", description = "Retrieves a paginated list of processes created by the currently authenticated user.")
    public BaseResponse<PageResponse<ProcessDetailInformationViewDTO>> getByUser(@RequestBody LazyLoadEventDTO eventDTO) {
        return BaseResponse.success(PageResponse.from(processViewService.byUser(eventDTO)));
    }

    @PostMapping("/pending")
    @Operation(summary = "Get pending processes", description = "Retrieves a paginated list of processes that are pending action from the current user's workflow tasks.")
    public BaseResponse<PageResponse<ProcessDetailInformationViewDTO>> getPending(@RequestBody LazyLoadEventDTO eventDTO) {
        return BaseResponse.success(PageResponse.from(processViewService.pending(eventDTO)));
    }

    @GetMapping("/detail/{processDetailId}")
    @Operation(summary = "Get process detail", description = "Retrieves the detailed information for a specific process history version.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved process detail"),
            @ApiResponse(responseCode = "404", description = "Process detail not found")
    })
    public BaseResponse<ProcessDetailDTO> getDetail(@Parameter(description = "ID of the process history record", required = true) @PathVariable String processDetailId) {
        return BaseResponse.success(processViewService.detail(processDetailId));
    }

    @PostMapping("/available/{scope}")
    @Operation(summary = "Get available processes by scope", description = "Retrieves a paginated list of available (published) processes filtered by a specific scope.")
    public BaseResponse<PageResponse<ProcessDetailInformationViewDTO>> getAvailable(
            @Parameter(description = "The scope of the processes to retrieve", required = true) @PathVariable ProcessScopeEnum scope,
            @RequestBody LazyLoadEventDTO eventDTO) {
        return BaseResponse.success(PageResponse.from(processViewService.available(scope, eventDTO)));
    }
}