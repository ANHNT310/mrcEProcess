package com.bpm.mrceprocess.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "related_document_template")
public class RelatedDocumentTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;

    private String fileId;

    @ManyToOne
    @JoinColumn(name = "related_document_id", referencedColumnName = "id")
    private RelatedDocument relatedDocument;
}
