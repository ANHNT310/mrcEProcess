package com.bpm.mrceprocess.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "related_document")
public class RelatedDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "general_information_history_id")
    private GeneralInformationHistory generalInformationHistory;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "related_document_legal_provision",
            joinColumns = @JoinColumn(name = "related_document_id")
    )
    @Column(name = "legal_provision")
    private List<String> legalProvisions;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "related_document_internal_document",
            joinColumns = @JoinColumn(name = "related_document_id")
    )
    @Column(name = "internal_document")
    private List<String> internalDocument;

    @OneToMany(mappedBy = "relatedDocument", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<RelatedDocumentTemplate> templates = new HashSet<>();
}
