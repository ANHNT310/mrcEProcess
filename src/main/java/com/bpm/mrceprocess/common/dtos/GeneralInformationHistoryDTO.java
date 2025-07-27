package com.bpm.mrceprocess.common.dtos;

import com.bpm.mrceprocess.common.enums.EffectiveType;
import com.bpm.mrceprocess.common.enums.ProcessInformationHistStage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeneralInformationHistoryDTO implements Serializable {
    String id;
    EffectiveType effectiveType;
    LocalDateTime effectiveDate;
    LocalDateTime endDate;
    Integer duration;
    LocalDateTime createdAt;
    String createdBy;
    LocalDateTime updatedAt;
    String updatedBy;
    Integer version;
    String diagramId;
    String objectId;
    String businessCode;
    ProcessInformationHistStage stage;
    String categoryId;
}