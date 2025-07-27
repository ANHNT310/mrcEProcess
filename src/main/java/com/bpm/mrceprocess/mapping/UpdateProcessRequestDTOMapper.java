package com.bpm.mrceprocess.mapping;

import com.bpm.mrceprocess.common.dtos.UpdateProcessRequestDTO;
import com.bpm.mrceprocess.persistence.entity.GeneralInformation;
import com.bpm.mrceprocess.persistence.entity.GeneralInformationHistory;
import org.mapstruct.*;

// Thêm 'uses' để nhất quán với CreateProcessRequestDTOMapper
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {TermAbbreviationMapper.class, RelatedDocumentMapper.class,
                OriginalDocumentMapper.class, DiagramDescriptionMapper.class,
                RelatedDocumentTemplateMapper.class})
public interface UpdateProcessRequestDTOMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "objectId", source = "information.objectId")
    @Mapping(target = "endDate", source = "information.endDate")
    @Mapping(target = "effectiveType", source = "information.effectiveType")
    @Mapping(target = "effectiveDate", source = "information.effectiveDate")
    @Mapping(target = "duration", source = "information.duration")
    @Mapping(target = "diagramId", source = "diagram.diagramId")
    // --- Bỏ qua tất cả các trường phức tạp, collection, và các trường chỉ đọc ---
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "stage", ignore = true)
    @Mapping(target = "businessCode", ignore = true)
    @Mapping(target = "generalInformation", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "originalDocuments", ignore = true)
    @Mapping(target = "diagramDescriptions", ignore = true)
    @Mapping(target = "termAbbreviations", ignore = true)
    @Mapping(target = "relatedDocument", ignore = true)
    void partialUpdate(UpdateProcessRequestDTO.Request request, @MappingTarget GeneralInformationHistory history);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "shortDescription", source = "information.shortDescription")
    @Mapping(target = "scope", source = "information.scope")
    @Mapping(target = "name", source = "information.name")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "histories", ignore = true)
    @Mapping(target = "code", ignore = true)
    @Mapping(target = "available", ignore = true)
    void partialUpdate(UpdateProcessRequestDTO.Request request, @MappingTarget GeneralInformation information);
}