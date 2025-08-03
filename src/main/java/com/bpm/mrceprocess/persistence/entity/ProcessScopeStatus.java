package com.bpm.mrceprocess.persistence.entity;

import com.bpm.mrceprocess.common.enums.ProcessStatusSeverityEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "mst_process_scope_status")
public class ProcessScopeStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String taskName;

    private String name;

    @Enumerated(EnumType.STRING)
    private ProcessStatusSeverityEnum severity;

    @ManyToOne
    @JoinColumn(name = "scope_id", referencedColumnName = "id")
    private ProcessScopeConfig scope;

    @OneToMany(mappedBy = "status", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<GeneralInformationHistory> processes = new HashSet<>();
}
