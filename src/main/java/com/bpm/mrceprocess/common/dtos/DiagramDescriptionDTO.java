package com.bpm.mrceprocess.common.dtos;

import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;

@Value
public class DiagramDescriptionDTO implements Serializable {
    LocalDateTime createdAt;
    String createdBy;
    LocalDateTime updatedAt;
    String updatedBy;
    String id;
    String step;
    String description;
    String responsibility;
    String sla;
    String referenceDocument;
}