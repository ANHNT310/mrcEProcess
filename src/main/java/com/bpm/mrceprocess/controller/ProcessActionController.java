package com.bpm.mrceprocess.controller;

import com.bpm.dtos.BaseResponse;
import com.bpm.mrceprocess.common.dtos.*;
import com.bpm.mrceprocess.common.enums.ProcessActionSaveType;
import com.bpm.mrceprocess.service.ProcessActionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/process/action")
@RequiredArgsConstructor
@Tag(name = "Process Action", description = "APIs for executing actions on processes (create, update, delete, etc.)")
public class ProcessActionController {

    private final ProcessActionService processActionService;

    @PostMapping("/save/{type}")
    @Operation(summary = "Create a new process as a draft", description = "Saves a new process in a draft state without starting a workflow.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Draft created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public BaseResponse<SaveProcessResponseDTO> save(@RequestBody SaveProcessRequestDTO request,
                                                     @PathVariable ProcessActionSaveType type) {
        return BaseResponse.success(processActionService.save(type, request));
    }

    @DeleteMapping("/delete/draft/{historyId}")
    @Operation(summary = "Delete a process draft", description = "Deletes a process that is in a draft state.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Draft deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Draft not found")
    })
    public BaseResponse<Void> deleteDraft(@Parameter(description = "ID of the process history record to delete", required = true) @PathVariable String historyId) {
        processActionService.deleteDraft(historyId);
        return BaseResponse.success(null);
    }

    @PutMapping("/public/{historyId}")
    @Operation(summary = "Make a process public", description = "Marks a finalized process version as public and available.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Process made public successfully"),
            @ApiResponse(responseCode = "404", description = "Process not found")
    })
    public BaseResponse<Void> publicProcess(@Parameter(description = "ID of the process history record to make public", required = true) @PathVariable String historyId) {
        processActionService.publicProcess(historyId);
        return BaseResponse.success(null);
    }

    @PutMapping("/deactivate/{historyId}")
    @Operation(summary = "Deactivate a process", description = "Deactivates an active process, making it unavailable.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Process deactivated successfully"),
            @ApiResponse(responseCode = "404", description = "Process not found")
    })
    public BaseResponse<Void> deactivateProcess(@Parameter(description = "ID of the process history record to deactivate", required = true) @PathVariable String historyId) {
        processActionService.deactivateProcess(historyId);
        return BaseResponse.success(null);
    }

    @PostMapping("/deactivate")
    @Operation(summary = "Submit a process for deactivation", description = "Submits a request to deactivate an active process, initiating a workflow if required.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Process deactivation submitted successfully"),
            @ApiResponse(responseCode = "404", description = "Process not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public BaseResponse<DeactivateProcessResponseDTO> deactivate(@RequestBody DeactivateProcessRequestDTO request) {
        return BaseResponse.success(processActionService.deactivate(request));
    }

}