package com.bpm.mrceprocess.mapping;

import com.bpm.mrceprocess.common.dtos.CreateProcessRequestDTO;
import com.bpm.mrceprocess.persistence.entity.RelatedDocumentTemplate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface RelatedDocumentTemplateMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "relatedDocument", ignore = true)
    RelatedDocumentTemplate toEntity(CreateProcessRequestDTO.Request.RelatedDocument.Template template);
}