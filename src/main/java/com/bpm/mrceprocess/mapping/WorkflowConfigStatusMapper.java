package com.bpm.mrceprocess.mapping;

import com.bpm.mrceprocess.common.dtos.WorkflowConfigStatusDTO;
import com.bpm.mrceprocess.persistence.entity.WorkflowConfigStatus;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface WorkflowConfigStatusMapper {
    WorkflowConfigStatus toEntity(WorkflowConfigStatusDTO workflowConfigStatusDTO);

    WorkflowConfigStatusDTO toDto(WorkflowConfigStatus workflowConfigStatus);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    WorkflowConfigStatus partialUpdate(WorkflowConfigStatusDTO workflowConfigStatusDTO, @MappingTarget WorkflowConfigStatus workflowConfigStatus);
}