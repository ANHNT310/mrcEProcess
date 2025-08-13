package com.bpm.mrceprocess.persistence.entity;

import com.bpm.mrceprocess.common.enums.ProcessScopeEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "mst_process_scope")
public class ProcessScopeConfig extends AuditorProvider {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;

    private String workflowName;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", unique = true, nullable = false)
    private ProcessScopeEnum type;

    @OneToMany(mappedBy = "scope", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<ProcessScopeStatus> statuses = new HashSet<>();
    public void addStatus(ProcessScopeStatus status) {
        if (status != null) {
            this.statuses.add(status);
            status.setScope(this);
        }
    }
    public void removeStatus(ProcessScopeStatus status) {
        if (status != null) {
            this.statuses.remove(status);
            status.setScope(null);
        }
    }
}
