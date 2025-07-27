package com.bpm.mrceprocess.persistence.entity;

import com.bpm.mrceprocess.common.enums.DocumentStatus;
import com.bpm.mrceprocess.common.enums.EffectiveType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "original_document")
public class OriginalDocument extends AuditorProvider {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String code;

    private String subject;

    @Enumerated(EnumType.STRING)
    private DocumentStatus status;

    private String type;

    private LocalDateTime issuedDate;

    private String approvalAuthority;

    private boolean isEffectivenessSame = false;

    @Enumerated(EnumType.STRING)
    private EffectiveType effectiveType;

    private Integer duration;

    private LocalDateTime effectiveDate;

    private LocalDateTime endDate;

    private String shareFor;

    private String documentLink;

    private String attachmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "general_information_history_id")
    private GeneralInformationHistory generalInformationHistory;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "original_document_specific_user",
            joinColumns = @JoinColumn(name = "original_document_id")
    )
    @Column(name = "specific_user")
    private Set<String> specificUsers = new HashSet<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "original_document_specific_unit",
            joinColumns = @JoinColumn(name = "original_document_id")
    )
    @Column(name = "specific_unit")
    private Set<String> specificUnits = new HashSet<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "original_document_relevant_unit",
            joinColumns = @JoinColumn(name = "original_document_id")
    )
    @Column(name = "relevant_unit")
    private Set<String> relevantUnits = new HashSet<>();
}
