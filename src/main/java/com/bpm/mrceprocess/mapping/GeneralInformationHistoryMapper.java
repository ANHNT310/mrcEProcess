package com.bpm.mrceprocess.mapping;

import com.bpm.mrceprocess.common.dtos.GeneralInformationHistoryDTO;
import com.bpm.mrceprocess.common.dtos.GeneralInformationViewDTO;
import com.bpm.mrceprocess.common.dtos.GeneralWorkflowViewDTO;
import com.bpm.mrceprocess.external.payload.WorkflowTaskSummaryDTO;
import com.bpm.mrceprocess.persistence.entity.GeneralInformationHistory;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {MappingHelper.class})
public interface GeneralInformationHistoryMapper {

    GeneralInformationHistory toEntity(GeneralInformationHistoryDTO generalInformationHistoryDTO);

    GeneralInformationHistoryDTO toDto(GeneralInformationHistory generalInformationHistory);

    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "effectiveDate", source = "entity.effectiveDate")
    @Mapping(target = "endDate", source = "entity.endDate")
    @Mapping(target = "effectiveType", source = "entity.effectiveType")
    @Mapping(target = "duration", source = "entity.duration")
    @Mapping(target = "version", source = "entity.version")
    @Mapping(target = "historyCreatedBy", source = "entity.createdBy")
    @Mapping(target = "historyCreatedDate", source = "entity.createdAt")
    @Mapping(target = "generalId", source = "entity.generalInformation.id")
    @Mapping(target = "code", source = "entity.generalInformation.code")
    @Mapping(target = "generalCreatedBy", source = "entity.generalInformation.createdBy")
    @Mapping(target = "generalCreatedDate", source = "entity.generalInformation.createdAt")
    @Mapping(target = "stage", expression = "java(taskSummaryDTOS.stream().filter(t -> t.getBusinessKey().equals(entity.getBusinessCode())).map(WorkflowTaskSummaryDTO::getTaskName).findFirst().orElse(null))")
    GeneralInformationViewDTO toDTO (GeneralInformationHistory entity, List<WorkflowTaskSummaryDTO> taskSummaryDTOS);

    @Mapping(target = "historyId", source = "id")
    @Mapping(target = "generalId", source = "generalInformation.id")
    @Mapping(target = "category", expression = "java(entity.getCategory().getVieName() + \'-\' + entity.getCategory().getEnName())")
    @Mapping(target = "effectiveType", source = "effectiveType")
    @Mapping(target = "effectiveDate", source = "effectiveDate")
    @Mapping(target = "endDate", source = "endDate")
    @Mapping(target = "duration", source = "duration")
    @Mapping(target = "version", source = "version")
    @Mapping(target = "createdDate", source = "createdAt")
    @Mapping(target = "stage", ignore = true)
    @Mapping(target = "taskId", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    GeneralWorkflowViewDTO toDTO (GeneralInformationHistory entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    GeneralInformationHistory partialUpdate(GeneralInformationHistoryDTO generalInformationHistoryDTO, @MappingTarget GeneralInformationHistory generalInformationHistory);
}
