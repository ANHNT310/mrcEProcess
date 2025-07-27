package com.bpm.mrceprocess.common.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeneralWorkflowViewDTO {

    private String generalId;
    private String historyId;
    private String scope;
    private String category;
    private String effectiveType;
    private LocalDateTime effectiveDate;
    private LocalDateTime endDate;
    private Integer duration;
    private Integer version;
    private String createdBy;
    private LocalDateTime createdDate;
    private String code;
    private String name;
    private String shortDescription;
    private String stage;
    private String businessCode;
    private String taskId;
}
