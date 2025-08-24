package com.bpm.mrceprocess.common.dtos;

import com.bpm.mrceprocess.common.enums.ProcessScopeEnum;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;

@Value
public class GeneralInformationDTO implements Serializable {
    String id;
    String code;
    LocalDateTime createdAt;
    String createdBy;
    LocalDateTime updatedAt;
    String updatedBy;
    String availableId;
}