package com.bpm.mrceprocess.controller;

import com.bpm.mrceprocess.common.dtos.CategoryDTO;
import com.bpm.mrceprocess.service.CategoryService;
import com.bpm.mrceprocess.service.CrudService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
@Tag(name = "Category Management", description = "APIs for managing process categories")
public class CategoryController extends AbstractCrudController<CategoryDTO, String> {

    private final CategoryService categoryService;

    @Override
    protected CrudService<?, CategoryDTO, String> getService() {
        return categoryService;
    }
}
