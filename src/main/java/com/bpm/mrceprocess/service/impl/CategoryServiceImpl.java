package com.bpm.mrceprocess.service.impl;

import com.bpm.mrceprocess.common.dtos.CategoryDTO;
import com.bpm.mrceprocess.mapping.CategoryMapper;
import com.bpm.mrceprocess.persistence.entity.Category;
import com.bpm.mrceprocess.persistence.repository.CategoryRepository;
import com.bpm.mrceprocess.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl extends AbstractCrudService<Category, CategoryDTO, String> implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    protected JpaRepository<Category, String> getRepository() {
        return categoryRepository;
    }

    @Override
    protected Category toEntity(CategoryDTO dto) {
        return categoryMapper.toEntity(dto);
    }

    @Override
    protected CategoryDTO toDTO(Category entity) {
        return categoryMapper.toDto(entity);
    }

    @Override
    protected Category toUpdateEntity(Category entity, CategoryDTO dto) {
        return categoryMapper.partialUpdate(dto, entity);
    }
}
