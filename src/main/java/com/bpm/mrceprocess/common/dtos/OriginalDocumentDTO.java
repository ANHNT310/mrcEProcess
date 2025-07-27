package com.bpm.mrceprocess.common.dtos;

import com.bpm.mrceprocess.common.enums.DocumentStatus;
import com.bpm.mrceprocess.common.enums.EffectiveType;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * DTO for {@link com.bpm.eProcess.persistence.entity.OriginalDocument}
 */
@Value
public class OriginalDocumentDTO implements Serializable {
    LocalDateTime createdAt;
    String createdBy;
    LocalDateTime updatedAt;
    String updatedBy;
    String id;
    String code;
    String subject;
    DocumentStatus status;
    String type;
    LocalDateTime issuedDate;
    String approvalAuthority;
    boolean effectivenessSame;
    EffectiveType effectiveType;
    Integer duration;
    LocalDateTime effectiveDate;
    LocalDateTime endDate;
    String shareFor;
    String documentLink;
    String attachmentId;
    Set<String> specificUsers;
    Set<String> specificUnits;
    Set<String> relevantUnits;
}