package com.bpm.mrceprocess.common.dtos;

import com.bpm.mrceprocess.common.enums.ProcessStatusSeverityEnum;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.bpm.mrceprocess.persistence.entity.ProcessScopeStatus}
 */
@Value
public class ProcessScopeStatusDTO implements Serializable {
    String id;
    String taskName;
    String name;
    ProcessStatusSeverityEnum severity;
}