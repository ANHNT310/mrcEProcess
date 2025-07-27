package com.bpm.mrceprocess.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import java.time.Instant;
import java.time.LocalDateTime;

/**
 * Mapping for DB view
 */
@Getter
@Setter
@Entity
@Immutable
@Table(name = "process_detail_information_view", schema = "e-process-service")
public class ProcessDetailInformationView {
    @Size(max = 255)
    @Column(name = "general_id")
    private String generalId;

    @Size(max = 255)
    @Column(name = "general_code")
    private String generalCode;

    @Size(max = 255)
    @Column(name = "general_scope")
    private String generalScope;

    @Size(max = 255)
    @Column(name = "general_name")
    private String generalName;

    @Size(max = 255)
    @Column(name = "general_short_desc")
    private String generalShortDesc;

    @Size(max = 255)
    @Id
    @Column(name = "history_id")
    private String historyId;

    @Size(max = 255)
    @Column(name = "business_code")
    private String businessCode;

    @Size(max = 255)
    @Column(name = "diagram_id")
    private String diagramId;

    @Size(max = 255)
    @Column(name = "object_id")
    private String objectId;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "effective_date")
    private LocalDateTime effectiveDate;

    @Size(max = 255)
    @Column(name = "effective_type")
    private String effectiveType;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Size(max = 255)
    @Column(name = "stage")
    private String stage;

    @Column(name = "version")
    private Integer version;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "created_by")
    private String createdBy;

}