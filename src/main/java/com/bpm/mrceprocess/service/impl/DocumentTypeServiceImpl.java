package com.bpm.mrceprocess.service.impl;

import com.bpm.mrceprocess.common.dtos.DocumentTypeDTO;
import com.bpm.mrceprocess.mapping.DocumentTypeMapper;
import com.bpm.mrceprocess.persistence.entity.DocumentType;
import com.bpm.mrceprocess.persistence.repository.DocumentTypeRepository;
import com.bpm.mrceprocess.service.DocumentTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DocumentTypeServiceImpl extends AbstractCrudService<DocumentType, DocumentTypeDTO, String> implements DocumentTypeService {

    private final DocumentTypeRepository documentTypeRepository;
    private final DocumentTypeMapper documentTypeMapper;

    @Override
    protected JpaRepository<DocumentType, String> getRepository() {
        return documentTypeRepository;
    }

    @Override
    protected DocumentType toEntity(DocumentTypeDTO dto) {
        return documentTypeMapper.toEntity(dto);
    }

    @Override
    protected DocumentTypeDTO toDTO(DocumentType entity) {
        return documentTypeMapper.toDto(entity);
    }

    @Override
    protected DocumentType toUpdateEntity(DocumentType entity, DocumentTypeDTO dto) {
        return documentTypeMapper.partialUpdate(dto, entity);
    }
}
