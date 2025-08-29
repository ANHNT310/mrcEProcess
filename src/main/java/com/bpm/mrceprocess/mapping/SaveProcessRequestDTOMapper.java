package com.bpm.mrceprocess.mapping;

import com.bpm.mrceprocess.common.dtos.SaveProcessRequestDTO;
import com.bpm.mrceprocess.persistence.entity.*;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {MappingHelper.class})
public interface SaveProcessRequestDTOMapper {

    @Mappings({
            @Mapping(target = "name", source = "information.name"),
            @Mapping(target = "shortDescription", source = "information.shortDescription"),
            @Mapping(target = "category", source = "information.category", qualifiedByName = "categoryFromId"),
            @Mapping(target = "workflow", source = "information.workflow", qualifiedByName = "workflowConfigFromId"),
            @Mapping(target = "effectiveType", source = "information.effectiveType"),
            @Mapping(target = "effectiveDate", source = "information.effectiveDate"),
            @Mapping(target = "endDate", source = "information.endDate"),
            @Mapping(target = "duration", source = "information.duration"),
            @Mapping(target = "diagramId", source = "diagram.diagramId"),
            @Mapping(target = "objectId", source = "information.objectId"),
            @Mapping(target = "originalDocuments", source = "information.originalDocuments"),
            @Mapping(target = "diagramDescriptions", source = "diagram.descriptions"),
            @Mapping(target = "termAbbreviations", source = "termAbbreviations"),
            @Mapping(target = "relatedDocument", source = "relatedDocument"),
            // Ignore fields that are not in the DTO or should be set manually
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "generalInformation", ignore = true),
            @Mapping(target = "version", ignore = true),
            @Mapping(target = "deactivate", ignore = true),
            @Mapping(target = "status", ignore = true),
            @Mapping(target = "generalInformationHistoryTickets", ignore = true),
            @Mapping(target = "draft", ignore = true)
    })
    GeneralInformationHistory fromDTO(SaveProcessRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "generalInformationHistory", ignore = true)
    @Mapping(target = "duration", source = "duration")
    OriginalDocument fromDTO (SaveProcessRequestDTO.Information.OriginalDocument dto);

    @Mapping(target = "referenceDocument", source = "referenceDoc")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "generalInformationHistory", ignore = true)
    DiagramDescription fromDTO(SaveProcessRequestDTO.Diagram.Description dto);

    @Mapping(target = "vieDefinition", source = "vietnamese")
    @Mapping(target = "enDefinition", source = "english")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "generalInformationHistory", ignore = true)
    TermAbbreviation fromDTO(SaveProcessRequestDTO.TermAbbreviation dto);

    @Mapping(target = "internalDocument", source = "internalDocuments")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "generalInformationHistory", ignore = true)
    RelatedDocument fromDTO(SaveProcessRequestDTO.RelatedDocument dto);

    // Note: The target entity is 'RelatedDocumentTemplate'.
    // MapStruct will map fields with the same name automatically (name, fileId).
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "relatedDocument", ignore = true)
    RelatedDocumentTemplate fromDTO(SaveProcessRequestDTO.RelatedDocument.Template dto);

    /**
     * Updates an existing GeneralInformationHistory entity from a DTO.
     * @param history The entity to update, annotated with @MappingTarget.
     * @param dto The DTO containing the new data.
     */
    @Mappings({
            @Mapping(target = "name", source = "information.name"),
            @Mapping(target = "shortDescription", source = "information.shortDescription"),
            @Mapping(target = "category", source = "information.category", qualifiedByName = "categoryFromId"),
            @Mapping(target = "effectiveType", source = "information.effectiveType"),
            @Mapping(target = "effectiveDate", source = "information.effectiveDate"),
            @Mapping(target = "endDate", source = "information.endDate"),
            @Mapping(target = "duration", source = "information.duration"),
            @Mapping(target = "diagramId", source = "diagram.diagramId"),
            @Mapping(target = "objectId", source = "information.objectId"),
            @Mapping(target = "originalDocuments", source = "information.originalDocuments"),
            @Mapping(target = "diagramDescriptions", source = "diagram.descriptions"),
            @Mapping(target = "termAbbreviations", source = "termAbbreviations"),
            @Mapping(target = "relatedDocument", source = "relatedDocument"),
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "generalInformation", ignore = true),
            @Mapping(target = "version", ignore = true),
            @Mapping(target = "deactivate", ignore = true),
            @Mapping(target = "status", ignore = true),
            @Mapping(target = "generalInformationHistoryTickets", ignore = true),
            @Mapping(target = "draft", ignore = true)
    })
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDTO(@MappingTarget GeneralInformationHistory history, SaveProcessRequestDTO dto);


    @AfterMapping
    default void setParentReferences(@MappingTarget GeneralInformationHistory history){
        // the original documents need the history entity to know the general_information_history_id
        if(history != null && history.getOriginalDocuments() != null){
            for(OriginalDocument doc : history.getOriginalDocuments()){
                doc.setGeneralInformationHistory(history);
            }
        }
        // this need the history to know the history id too.
        if(history != null && history.getRelatedDocument() != null){
            history.getRelatedDocument().setGeneralInformationHistory(history);
        }
    }

    @AfterMapping
    default void setRelatedDocument(@MappingTarget RelatedDocument relatedDocument) {
        if (relatedDocument != null && relatedDocument.getTemplates() != null) {
            relatedDocument.getTemplates().forEach(item -> item.setRelatedDocument(relatedDocument));
        }
    }
}
