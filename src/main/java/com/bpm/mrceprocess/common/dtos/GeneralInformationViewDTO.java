package com.bpm.mrceprocess.common.dtos;

import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;

@Value
public class GeneralInformationViewDTO implements Serializable {
    String id;
    String scope;
    String categoryVIE;
    String categoryEn;
    String effectiveType;
    LocalDateTime effectiveDate;
    LocalDateTime endDate;
    Integer duration;
    Integer version;
    String historyCreatedBy;
    LocalDateTime historyCreatedDate;
    String generalId;
    String code;
    String name;
    String shortDescription;
    String generalCreatedBy;
    LocalDateTime generalCreatedDate;
    String stage;
    String businessKey;
}