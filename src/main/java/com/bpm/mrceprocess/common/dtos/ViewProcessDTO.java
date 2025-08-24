package com.bpm.mrceprocess.common.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ViewProcessDTO {

    private String generalId;

    private String historyId;

    private String code;

    private String name;

    private String shortDescription;

    private Integer version;

    private String createdBy;

    private LocalDateTime createdAt;

    private String historyCreatedBy;

    private LocalDateTime historyCreatedAt;
}
