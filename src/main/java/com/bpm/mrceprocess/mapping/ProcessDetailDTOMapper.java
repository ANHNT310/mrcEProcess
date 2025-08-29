package com.bpm.mrceprocess.mapping;

import com.bpm.mrceprocess.common.dtos.ProcessDetailDTO;
import com.bpm.mrceprocess.persistence.entity.GeneralInformationHistory;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {OriginalDocumentMapper.class, DiagramDescriptionMapper.class,
                GeneralInformationMapper.class, GeneralInformationHistoryMapper.class,
                TermAbbreviationMapper.class, RelatedDocumentMapper.class, CategoryMapper.class,
                RelatedDocumentTemplateMapper.class})
public interface ProcessDetailDTOMapper {

    @Mapping(target = "informationHistory", source = "generalInformationHistory")
    @Mapping(target = "generalInformation", source = "generalInformationHistory.generalInformation")
    @Mapping(target = "processDocuments", source = "generalInformationHistory.originalDocuments")
    @Mapping(target = "relatedDocuments", source = "generalInformationHistory.relatedDocument")
    @Mapping(target = "diagramDescriptions", source = "generalInformationHistory.diagramDescriptions")
    @Mapping(target = "termAbbreviations", source = "generalInformationHistory.termAbbreviations")
    @Mapping(target = "relatedDocuments.internalDocuments", source = "generalInformationHistory.relatedDocument.internalDocument")
    ProcessDetailDTO toDTO (GeneralInformationHistory generalInformationHistory);

    @AfterMapping
    default void mapCategoryId(@MappingTarget ProcessDetailDTO dto, GeneralInformationHistory source) {
        if (source != null && source.getCategory() != null) {
            dto.getInformationHistory().setCategoryId(source.getCategory().getId());
        }
    }
}
