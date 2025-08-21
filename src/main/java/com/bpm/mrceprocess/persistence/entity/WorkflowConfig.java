package com.bpm.mrceprocess.persistence.entity;

import com.bpm.mrceprocess.common.enums.GeneralInformationType;
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

    @OneToMany(mappedBy = "workflowConfig", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<WorkflowConfigStatus> statuses = new HashSet<>();
    public void addStatus(WorkflowConfigStatus status) {
        if (status != null) {
            this.statuses.add(status);
            status.setWorkflowConfig(this);
        }
    }
    public void removeStatus(WorkflowConfigStatus status) {
        if (status != null) {
            this.statuses.remove(status);
            status.setWorkflowConfig(null);
        }
    }
}
