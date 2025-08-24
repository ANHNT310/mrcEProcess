package com.bpm.mrceprocess.common.dtos;

import com.bpm.mrceprocess.common.enums.GeneralInformationType;
import com.bpm.mrceprocess.common.enums.WorkflowConfigScope;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.bpm.mrceprocess.persistence.entity.WorkflowConfig}
 */
@Value
public class WorkflowConfigDTO implements Serializable {
    String id;
    String workflowName;
    String displayName;
    GeneralInformationType type;
    WorkflowConfigScope scope;
}