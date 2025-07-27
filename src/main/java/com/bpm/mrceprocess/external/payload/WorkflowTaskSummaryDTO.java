package com.bpm.mrceprocess.external.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkflowTaskSummaryDTO {
    private String taskId;
    private String taskName;
    private String processInstanceId;
    private String processDefinitionName;
    private LocalDateTime createdDate;
    private String businessKey;
}
