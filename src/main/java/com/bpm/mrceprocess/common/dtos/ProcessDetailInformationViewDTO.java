package com.bpm.mrceprocess.common.dtos;

import jakarta.validation.constraints.Size;
import lombok.Value;
import org.springframework.cglib.core.Local;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;

@Value
public class ProcessDetailInformationViewDTO implements Serializable {
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
}