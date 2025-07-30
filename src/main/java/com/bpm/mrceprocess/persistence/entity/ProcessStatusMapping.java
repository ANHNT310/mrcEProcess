package com.bpm.mrceprocess.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Formula;

@Getter
@Setter
@Entity
@Table(name = "process_status_mapping")
public class ProcessStatusMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String taskName;

    private String status;

    private String scope;

    @Column(name = "default_status")
    private boolean defaultStatus = false;

    @Formula("case when default_status = true then scope else null end")
    @Column(unique = true, insertable = false, updatable = false)
    private String uniqueScopeForDefault;
}
