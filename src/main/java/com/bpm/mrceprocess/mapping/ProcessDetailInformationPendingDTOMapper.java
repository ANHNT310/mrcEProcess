package com.bpm.mrceprocess.mapping;

import com.bpm.mrceprocess.common.dtos.ProcessDetailInformationPendingDTO;
import com.bpm.mrceprocess.external.payload.WorkflowTaskSummaryDTO;
import com.bpm.mrceprocess.persistence.entity.GeneralInformationHistory;
import com.bpm.mrceprocess.persistence.entity.ProcessDetailInformationView;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProcessDetailInformationPendingDTOMapper {

    @Mapping(target = "taskId", source = "workflowTask.taskId")
    @Mapping(target = "taskName", source = "workflowTask.taskName")
    @Mapping(target = "processDefinitionName", source = "workflowTask.processDefinitionName")
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
    ProcessDetailInformationPendingDTO toDTO(ProcessDetailInformationView view, WorkflowTaskSummaryDTO workflowTask);

    @Mapping(target = "taskId", source = "workflow.taskId")
    @Mapping(target = "taskName", source = "workflow.taskName")
    @Mapping(target = "processDefinitionName", source = "workflow.processDefinitionName")
    @Mapping(target = "businessCode", source = "workflow.businessKey")
    @Mapping(target = "generalId", source = "history.generalInformation.id")
    @Mapping(target = "generalName", source = "history.name")
    @Mapping(target = "generalScope", source = "history.workflow.type")
    @Mapping(target = "generalShortDesc", source = "history.shortDescription")
    @Mapping(target = "historyId", source = "history.id")
    @Mapping(target = "duration", source = "history.duration")
    @Mapping(target = "effectiveDate", source = "history.effectiveDate")
    @Mapping(target = "effectiveType", source = "history.effectiveType")
    @Mapping(target = "endDate", source = "history.endDate")
    @Mapping(target = "stage", source = "history.status")
    @Mapping(target = "version", source = "history.version")
    @Mapping(target = "createdAt", source = "history.createdAt")
    @Mapping(target = "createdBy", source = "history.createdBy")
    @Mapping(target = "diagramId", source = "history.diagramId")
    @Mapping(target = "objectId", source = "history.objectId")
    @Mapping(target = "generalCode", source = "history.generalInformation.code")
    ProcessDetailInformationPendingDTO toDTO(GeneralInformationHistory history, WorkflowTaskSummaryDTO workflow);
}
