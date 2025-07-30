package com.bpm.mrceprocess.common.dtos;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.bpm.mrceprocess.persistence.entity.ProcessStatusMapping}
 */
@Value
public class ProcessStatusMappingDTO implements Serializable {
    String id;
    String taskName;
    String name;
    String scope;
    boolean defaultStatus;
    String uniqueScopeForDefault;
}