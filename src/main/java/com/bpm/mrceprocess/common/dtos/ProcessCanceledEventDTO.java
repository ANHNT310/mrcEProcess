package com.bpm.mrceprocess.common.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProcessCanceledEventDTO {

    private String businessKey;
    private String processInstanceId;
    private String processDefinitionId;
    private String tenantId;
    private String reason;
    private String actionBy;
}
