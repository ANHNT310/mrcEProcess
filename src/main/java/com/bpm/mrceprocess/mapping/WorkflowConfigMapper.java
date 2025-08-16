package com.bpm.mrceprocess.mapping;

import com.bpm.mrceprocess.common.dtos.WorkflowConfigDTO;
import com.bpm.mrceprocess.persistence.entity.WorkflowConfig;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface WorkflowConfigMapper {
    WorkflowConfig toEntity(WorkflowConfigDTO workflowConfigDTO);

    WorkflowConfigDTO toDto(WorkflowConfig workflowConfig);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    WorkflowConfig partialUpdate(WorkflowConfigDTO workflowConfigDTO, @MappingTarget WorkflowConfig workflowConfig);
}