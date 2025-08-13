package com.bpm.mrceprocess.persistence.entity;

import com.bpm.mrceprocess.common.enums.GeneralInformationType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "general_information_workflow")
public class GeneralInformationWorkflow {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String workflowName;

    private String displayName;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", unique = true, nullable = false)
    private GeneralInformationType type;

    private boolean submit = false;

    private boolean deactivate = false;
}
