package com.bpm.mrceprocess.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "diagram_description")
public class DiagramDescription extends AuditorProvider {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String step;

    @Lob
    private String description;

    private String responsibility;

    private String sla;

    private String referenceDocument;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "general_information_history_id")
    private GeneralInformationHistory generalInformationHistory;
}
