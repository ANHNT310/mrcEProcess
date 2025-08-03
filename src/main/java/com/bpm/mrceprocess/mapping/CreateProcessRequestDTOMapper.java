package com.bpm.mrceprocess.mapping;

import com.bpm.mrceprocess.common.dtos.CreateProcessRequestDTO;
import com.bpm.mrceprocess.persistence.entity.GeneralInformation;
import com.bpm.mrceprocess.persistence.entity.GeneralInformationHistory;
import com.bpm.mrceprocess.persistence.entity.RelatedDocument;
import org.mapstruct.*;
import org.springframework.util.CollectionUtils;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {TermAbbreviationMapper.class, RelatedDocumentMapper.class,
                OriginalDocumentMapper.class, DiagramDescriptionMapper.class,
                RelatedDocumentTemplateMapper.class, MappingHelper.class})
public interface CreateProcessRequestDTOMapper {

    @Mapping(target = "status", ignore = true)
    @Mapping(target = "originalDocuments", source = "information.originalDocuments")
    @Mapping(target = "objectId", source = "information.objectId")
    @Mapping(target = "endDate", source = "information.endDate")
    @Mapping(target = "effectiveType", source = "information.effectiveType")
    @Mapping(target = "effectiveDate", source = "information.effectiveDate")
    @Mapping(target = "duration", source = "information.duration")
    @Mapping(target = "diagramId", source = "diagram.diagramId")
    @Mapping(target = "diagramDescriptions", source = "diagram.descriptions")
    @Mapping(target = "termAbbreviations", source = "termAbbreviations")
    @Mapping(target = "relatedDocument", source = "relatedDocument")
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "businessCode", ignore = true)
    @Mapping(target = "generalInformation", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "id", ignore = true)
    GeneralInformationHistory toGeneralInformationHistory (CreateProcessRequestDTO.Request request);

    @Mapping(target = "shortDescription", source = "information.shortDescription")
    @Mapping(target = "scope", source = "information.scope", qualifiedByName = "stringToProcessScopeConfig")
    @Mapping(target = "name", source = "information.name")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "histories", ignore = true)
    @Mapping(target = "code", ignore = true)
    @Mapping(target = "available", ignore = true)
    GeneralInformation toGeneralInformation (CreateProcessRequestDTO.Request request);

    @AfterMapping
    default void establishBidirectionalRelationships(@MappingTarget GeneralInformationHistory history) {
        if (!CollectionUtils.isEmpty(history.getOriginalDocuments())) {
            history.getOriginalDocuments().forEach(doc -> doc.setGeneralInformationHistory(history));
        }
        if (!CollectionUtils.isEmpty(history.getDiagramDescriptions())) {
            history.getDiagramDescriptions().forEach(desc -> desc.setGeneralInformationHistory(history));
        }
        if (!CollectionUtils.isEmpty(history.getTermAbbreviations())) {
            history.getTermAbbreviations().forEach(term -> term.setGeneralInformationHistory(history));
        }
        RelatedDocument relatedDoc = history.getRelatedDocument();
        if (relatedDoc != null) {
            relatedDoc.setGeneralInformationHistory(history);
        }
    }
}
