package com.bpm.mrceprocess.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "original_document")
public class ProcessStatusMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String taskName;

    private String status;
}
