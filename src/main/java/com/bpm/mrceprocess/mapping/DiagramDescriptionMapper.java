package com.bpm.mrceprocess.mapping;

import com.bpm.mrceprocess.common.dtos.UpdateProcessRequestDTO;
import com.bpm.mrceprocess.persistence.entity.DiagramDescription;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface DiagramDescriptionMapper {

    // --- UPDATED: Added explicit mappings ---
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "step", source = "step")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "responsibility", source = "responsibility")
    @Mapping(target = "sla", source = "sla")
    // Assuming DTO field is 'referenceDoc' and Entity field is 'referenceDocument'
    @Mapping(target = "referenceDocument", source = "referenceDoc")
    DiagramDescription toEntity (UpdateProcessRequestDTO.Request.Diagram.Description description);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(UpdateProcessRequestDTO.Request.Diagram.Description dto, @MappingTarget DiagramDescription existingDesc);
}