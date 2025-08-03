package com.bpm.mrceprocess.common.dtos;

import com.bpm.mrceprocess.common.enums.ProcessScopeEnum;
import com.bpm.mrceprocess.common.enums.ProcessStatusSeverityEnum;
import com.bpm.mrceprocess.persistence.entity.ProcessScopeConfig;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

/**
 * DTO for {@link ProcessScopeConfig}
 */
@Value
public class ProcessScopeConfigDTO implements Serializable {
    LocalDateTime createdAt;
    String createdBy;
    LocalDateTime updatedAt;
    String updatedBy;
    String id;
    String name;
    String workflowName;
    ProcessScopeEnum type;
    Set<ProcessScopeStatusDTO> statuses;
}