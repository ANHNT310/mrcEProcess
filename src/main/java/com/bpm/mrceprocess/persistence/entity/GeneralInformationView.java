package com.bpm.mrceprocess.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Immutable;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Immutable
public class GeneralInformationView {

    @Id
    @Column(name = "history_id")
    private String id;

    @Column(name = "scope")
    private String scope;

    @Column(name = "process_category_id")
    private String processCategoryId;

    @Column(name = "effective_type")
    private String effectiveType;

    @Column(name = "effective_date")
    private LocalDateTime effectiveDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "version")
    private Integer version;

    @Column(name = "history_created_by")
    private String historyCreatedBy;

    @Column(name = "history_created_date")
    private LocalDateTime historyCreatedDate;

    @Column(name = "general_id")
    private String generalId;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "short_description")
    private String shortDescription;

    @Column(name = "general_created_by")
    private String generalCreatedBy;

    @Column(name = "general_created_date")
    private LocalDateTime generalCreatedDate;

    @Column(name = "category_vie")
    private String categoryVIE;

    @Column(name = "category_en")
    private String categoryEn;

    @Column(name = "stage")
    private String stage;
}