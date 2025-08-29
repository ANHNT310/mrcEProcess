package com.bpm.mrceprocess.persistence.entity;

import com.bpm.mrceprocess.common.enums.GeneralInformationType;
import com.bpm.mrceprocess.common.enums.WorkflowConfigScope;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "workflow_config")
public class WorkflowConfig extends AuditorProvider{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String workflowName;

    @Column(name = "display_name", unique = true, nullable = false)
    private String displayName;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", unique = true, nullable = false)
    private GeneralInformationType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "scope", nullable = false)
    private WorkflowConfigScope scope = WorkflowConfigScope.NEW;
}
