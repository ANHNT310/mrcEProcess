package com.bpm.mrceprocess.mapping;

import com.bpm.mrceprocess.common.dtos.ProcessDetailInformationViewDTO;
import com.bpm.mrceprocess.persistence.entity.ProcessDetailInformationView;
import org.mapstruct.*;

import java.util.Map;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProcessDetailInformationViewMapper {
    ProcessDetailInformationView toEntity(ProcessDetailInformationViewDTO processDetailInformationViewDTO);

    @Mapping(target = "taskId", ignore = true)
    ProcessDetailInformationViewDTO toDto(ProcessDetailInformationView processDetailInformationView);

    @Mapping(target = "taskId", expression = "java(workflowTask.getOrDefault(view.getBusinessCode(), null))")
    @Mapping(target = "businessCode", source = "view.businessCode")
    @Mapping(target = "generalId", source = "view.generalId")
    @Mapping(target = "generalName", source = "view.generalName")
    @Mapping(target = "generalScope", source = "view.generalScope")
    @Mapping(target = "generalShortDesc", source = "view.generalShortDesc")
    @Mapping(target = "historyId", source = "view.historyId")
    @Mapping(target = "duration", source = "view.duration")
    @Mapping(target = "effectiveDate", source = "view.effectiveDate")
    @Mapping(target = "effectiveType", source = "view.effectiveType")
    @Mapping(target = "endDate", source = "view.endDate")
    @Mapping(target = "stage", source = "view.stage")
    @Mapping(target = "version", source = "view.version")
    @Mapping(target = "createdAt", source = "view.createdAt")
    @Mapping(target = "createdBy", source = "view.createdBy")
    @Mapping(target = "diagramId", source = "view.diagramId")
    @Mapping(target = "objectId", source = "view.objectId")
    @Mapping(target = "generalCode", source = "view.generalCode")
    ProcessDetailInformationViewDTO toDTO(ProcessDetailInformationView view, Map<String, String> workflowTask);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ProcessDetailInformationView partialUpdate(ProcessDetailInformationViewDTO processDetailInformationViewDTO,
                                               @MappingTarget ProcessDetailInformationView processDetailInformationView);
}