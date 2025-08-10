package com.bpm.mrceprocess.external.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkflowStartPayloadResDTO {

    private String processInstanceId;
    private String processDefinitionId;
    private String businessKey;
}
