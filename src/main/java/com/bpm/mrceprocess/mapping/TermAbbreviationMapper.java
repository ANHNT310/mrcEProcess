package com.bpm.mrceprocess.mapping;

import com.bpm.mrceprocess.common.dtos.CreateProcessRequestDTO;
import com.bpm.mrceprocess.common.dtos.TermAbbreviationDTO;
import com.bpm.mrceprocess.common.dtos.UpdateProcessRequestDTO;
import com.bpm.mrceprocess.persistence.entity.TermAbbreviation;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TermAbbreviationMapper {


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "vieDefinition", source = "vietnamese")
    @Mapping(target = "enDefinition", source = "english")
    TermAbbreviation toEntity(CreateProcessRequestDTO.Request.TermAbbreviation termAbbreviation);

    @Mapping(target = "vieDefinition", source = "vietnamese")
    @Mapping(target = "enDefinition", source = "english")
    TermAbbreviation toEntity (UpdateProcessRequestDTO.Request.TermAbbreviation termAbbreviation);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "vieDefinition", source = "vieDefinition")
    @Mapping(target = "enDefinition", source = "enDefinition")
    TermAbbreviationDTO toDTO(TermAbbreviation termAbbreviation);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(UpdateProcessRequestDTO.Request.TermAbbreviation dto, @MappingTarget TermAbbreviation existingTerm);
}