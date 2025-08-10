package com.bpm.mrceprocess.common.dtos;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcessDetailInformationPendingDTO {

    @Size(max = 255)
    String generalId;

    @Size(max = 255)
    String generalCode;

    @Size(max = 255)
    String generalScope;

    @Size(max = 255)
    String generalName;

    @Size(max = 255)
    String generalShortDesc;

    @Size(max = 255)
    String historyId;

    @Size(max = 255)
    String businessCode;

    @Size(max = 255)
    String diagramId;

    @Size(max = 255)
    String objectId;

    Integer duration;

    LocalDateTime effectiveDate;

    @Size(max = 255)
    String effectiveType;

    LocalDateTime endDate;

    @Size(max = 255)
    String stage;

    Integer version;

    LocalDateTime createdAt;

    String createdBy;

    String taskId;

    String taskName;

    String processDefinitionName;
}
