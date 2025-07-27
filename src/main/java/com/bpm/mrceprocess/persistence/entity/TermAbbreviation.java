package com.bpm.mrceprocess.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "term_abbreviation")
public class TermAbbreviation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String vieDefinition;

    private String enDefinition;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "general_information_history_id")
    private GeneralInformationHistory generalInformationHistory;
}
