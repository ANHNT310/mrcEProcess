package com.bpm.mrceprocess.mapping;

import com.bpm.dtos.SelectItem;
import com.bpm.mrceprocess.common.dtos.DocumentTypeDTO;
import com.bpm.mrceprocess.persistence.entity.DocumentType;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface DocumentTypeMapper {
    DocumentType toEntity(DocumentTypeDTO documentTypeDTO);

    DocumentTypeDTO toDto(DocumentType documentType);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    DocumentType partialUpdate(DocumentTypeDTO documentTypeDTO, @MappingTarget DocumentType documentType);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", expression = "java(nameSelectItem(documentType))")
    SelectItem toSelectItem (DocumentType documentType);

    default String nameSelectItem(DocumentType documentType) {
        if (documentType == null) {
            return null;
        }

        return documentType.getViName() + " / " + documentType.getEnName();
    }
}