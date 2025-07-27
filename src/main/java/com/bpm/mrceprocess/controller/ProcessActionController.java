package com.bpm.mrceprocess.controller;

import com.bpm.dtos.BaseResponse;
import com.bpm.mrceprocess.common.dtos.CreateProcessRequestDTO;
import com.bpm.mrceprocess.common.dtos.UpdateProcessRequestDTO;
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

    @PostMapping("/create/draft")
    @Operation(summary = "Create a new process as a draft", description = "Saves a new process in a draft state without starting a workflow.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Draft created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public BaseResponse<CreateProcessRequestDTO.Response> createDraft(@RequestBody CreateProcessRequestDTO.Request request) {
        return BaseResponse.success(processActionService.createDraft(request));
    }

    @PostMapping("/create/submit")
    @Operation(summary = "Create and submit a new process", description = "Saves a new process and immediately submits it, starting a workflow.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Process created and submitted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public BaseResponse<CreateProcessRequestDTO.Response> createSubmit(@RequestBody CreateProcessRequestDTO.Request request) {
        return BaseResponse.success(processActionService.createSubmit(request));
    }

    @PutMapping("/update/draft")
    @Operation(summary = "Update an existing process as a draft", description = "Saves updates to an existing process in a draft state.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Draft updated successfully"),
            @ApiResponse(responseCode = "404", description = "Process not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public BaseResponse<UpdateProcessRequestDTO.Response> updateDraft(@RequestBody UpdateProcessRequestDTO.Request request) {
        return BaseResponse.success(processActionService.updateDraft(request));
    }

    @PutMapping("/update/submit")
    @Operation(summary = "Update and submit an existing process", description = "Saves updates to an existing process and immediately submits it, starting a workflow.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Process updated and submitted successfully"),
            @ApiResponse(responseCode = "404", description = "Process not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public BaseResponse<UpdateProcessRequestDTO.Response> updateSubmit(@RequestBody UpdateProcessRequestDTO.Request request) {
        return BaseResponse.success(processActionService.updateSubmit(request));
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
}