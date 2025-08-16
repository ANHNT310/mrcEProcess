package com.bpm.mrceprocess.common.dtos;

import com.bpm.mrceprocess.common.enums.ProcessStatusSeverityEnum;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.bpm.mrceprocess.persistence.entity.WorkflowConfigStatus}
 */
@Value
public class WorkflowConfigStatusDTO implements Serializable {
    String id;
    String taskName;
    String name;
    ProcessStatusSeverityEnum severity;
    boolean defaultBegin;
    boolean defaultEnd;
}