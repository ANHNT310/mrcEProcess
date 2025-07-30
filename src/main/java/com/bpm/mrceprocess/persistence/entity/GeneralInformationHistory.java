package com.bpm.mrceprocess.persistence.entity;

import com.bpm.mrceprocess.common.enums.EffectiveType;
import com.bpm.mrceprocess.common.enums.ProcessInformationHistStage;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "general_information_hist")
public class GeneralInformationHistory extends AuditorProvider {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "general_information_id", referencedColumnName = "id")
    private GeneralInformation generalInformation;

    @ManyToOne
    @JoinColumn(name = "process_category_id", referencedColumnName = "id")
    private Category category;

    @Enumerated(EnumType.STRING)
    private EffectiveType effectiveType;

    private LocalDateTime effectiveDate;

    private LocalDateTime endDate;

    private Integer duration;

    private Integer version;

    private String diagramId;

    private String objectId;

    private String businessCode;

    @ManyToOne
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    private ProcessStatusMapping status;

    @OneToMany(
            mappedBy = "generalInformationHistory",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<OriginalDocument> originalDocuments = new HashSet<>();

    public void addOriginalDocument(OriginalDocument document) {
        originalDocuments.add(document);
        document.setGeneralInformationHistory(this);
    }

    public void removeOriginalDocument(OriginalDocument document) {
        originalDocuments.remove(document);
        document.setGeneralInformationHistory(null);
    }

    @OneToMany(
            mappedBy = "generalInformationHistory",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<DiagramDescription> diagramDescriptions = new HashSet<>();

    public void addDiagramDescription(DiagramDescription description) {
        diagramDescriptions.add(description);
        description.setGeneralInformationHistory(this);
    }

    public void  removeDiagramDescription(DiagramDescription description) {
        diagramDescriptions.remove(description);
        description.setGeneralInformationHistory(null);
    }

    @OneToMany(
            mappedBy = "generalInformationHistory",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<TermAbbreviation> termAbbreviations = new HashSet<>();

    public void addTermAbbreviation(TermAbbreviation termAbbreviation) {
        termAbbreviations.add(termAbbreviation);
        termAbbreviation.setGeneralInformationHistory(this);
    }

    public void removeTermAbbreviation(TermAbbreviation termAbbreviation) {
        termAbbreviations.remove(termAbbreviation);
        termAbbreviation.setGeneralInformationHistory(null);
    }

    @OneToOne(
            mappedBy = "generalInformationHistory",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private RelatedDocument relatedDocument;
}
