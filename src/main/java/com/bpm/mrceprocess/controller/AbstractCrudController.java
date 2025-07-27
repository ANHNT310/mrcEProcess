package com.bpm.mrceprocess.controller;

import com.bpm.dtos.BaseResponse;
import com.bpm.dtos.LazyLoadEventDTO;
import com.bpm.dtos.PageResponse;
import com.bpm.mrceprocess.service.CrudService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public abstract class AbstractCrudController<D, ID>{

    protected abstract CrudService<?, D, ID> getService();

    @Operation(summary = "Get all items", description = "Retrieves a list of all items for the resource.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list")
    @GetMapping
    public BaseResponse<List<D>> getAll() {
        return BaseResponse.success(getService().findAll());
    }

    @Operation(summary = "Get paged items", description = "Retrieves a paginated list of items based on filter and sort criteria.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved paged list")
    @PostMapping("/paged")
    public BaseResponse<PageResponse<D>> paged (@RequestBody LazyLoadEventDTO eventDTO) {
        return BaseResponse.success(PageResponse.from(getService().paged(eventDTO)));
    }

    @Operation(summary = "Get item by ID", description = "Retrieves a single item by its unique identifier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved item"),
            @ApiResponse(responseCode = "404", description = "Item not found")
    })
    @GetMapping("/{id}")
    public BaseResponse<D> getById(@Parameter(description = "ID of the item to be obtained. Cannot be empty.", required = true) @PathVariable ID id) {
        return BaseResponse.success(getService().findById(id));
    }

    @Operation(summary = "Create a new item", description = "Creates a new item for the resource.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public BaseResponse<D> create(@RequestBody D dto) {
        return BaseResponse.success(getService().save(dto));
    }

    @Operation(summary = "Update an existing item", description = "Updates an existing item identified by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item updated successfully"),
            @ApiResponse(responseCode = "404", description = "Item not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PutMapping("/{id}")
    public BaseResponse<D> update(@Parameter(description = "ID of the item to be updated. Cannot be empty.", required = true) @PathVariable ID id, @RequestBody D dto) {
        return BaseResponse.success(getService().update(id, dto));
    }

    @Operation(summary = "Delete an item by ID", description = "Deletes an item by its unique identifier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Item not found")
    })
    @DeleteMapping("/{id}")
    public BaseResponse<Void> delete(@Parameter(description = "ID of the item to be deleted. Cannot be empty.", required = true) @PathVariable ID id) {
        getService().deleteById(id);
        return BaseResponse.success(null);
    }
}