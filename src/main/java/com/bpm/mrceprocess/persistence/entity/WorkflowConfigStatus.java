package com.bpm.mrceprocess.persistence.entity;

import com.bpm.mrceprocess.common.enums.ProcessStatusSeverityEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "workflow_config_status")
public class WorkflowConfigStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String taskName;

    private String name;

    @Enumerated(EnumType.STRING)
    private ProcessStatusSeverityEnum severity;

    private boolean defaultBegin = false;

    private boolean defaultEnd = false;
}
