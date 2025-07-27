package com.bpm.mrceprocess.mapping;

import com.bpm.mrceprocess.common.dtos.CreateProcessRequestDTO;
import com.bpm.mrceprocess.common.dtos.UpdateProcessRequestDTO;
import com.bpm.mrceprocess.persistence.entity.RelatedDocument;
import org.mapstruct.*;
import org.springframework.util.CollectionUtils;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING,
        uses = RelatedDocumentTemplateMapper.class)
public interface RelatedDocumentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "generalInformationHistory", ignore = true)
    @Mapping(target = "internalDocument", source = "internalDocuments")
    RelatedDocument toEntity(CreateProcessRequestDTO.Request.RelatedDocument dto);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "legalProvisions", source = "legalProvisions")
    @Mapping(target = "internalDocument", source = "internalDocuments")
    @Mapping(target = "templates", source = "templates")
    RelatedDocument toEntity (UpdateProcessRequestDTO.Request.RelatedDocument dto);

    @AfterMapping
    default void establishBidirectionalRelationships(@MappingTarget RelatedDocument entity) {
        if (!CollectionUtils.isEmpty(entity.getTemplates())) {
            entity.getTemplates().forEach(template -> template.setRelatedDocument(entity));
        }
    }
}