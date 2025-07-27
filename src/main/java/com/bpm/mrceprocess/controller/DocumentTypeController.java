package com.bpm.mrceprocess.controller;

import com.bpm.mrceprocess.common.dtos.DocumentTypeDTO;
import com.bpm.mrceprocess.service.CrudService;
import com.bpm.mrceprocess.service.DocumentTypeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/document-type")
@RequiredArgsConstructor
@Tag(name = "Document Type Management", description = "APIs for managing document types")
public class DocumentTypeController extends AbstractCrudController<DocumentTypeDTO, String> {

    private final DocumentTypeService documentTypeService;

    @Override
    protected CrudService<?, DocumentTypeDTO, String> getService() {
        return documentTypeService;
    }
}
