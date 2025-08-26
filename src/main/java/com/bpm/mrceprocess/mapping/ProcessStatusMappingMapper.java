package com.bpm.mrceprocess.mapping;

import com.bpm.mrceprocess.common.dtos.ProcessStatusMappingDTO;
import com.bpm.mrceprocess.persistence.entity.ProcessStatusMapping;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProcessStatusMappingMapper {

    ProcessStatusMapping toEntity(ProcessStatusMappingDTO processStatusMappingDTO);

    ProcessStatusMappingDTO toDto(ProcessStatusMapping processStatusMapping);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ProcessStatusMapping partialUpdate(ProcessStatusMappingDTO processStatusMappingDTO, @MappingTarget ProcessStatusMapping processStatusMapping);
}