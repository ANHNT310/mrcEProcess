package com.bpm.mrceprocess.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "general_information_hist_ticket")
public class GeneralInformationHistoryTicket extends AuditorProvider {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "general_information_hist_id")
    private GeneralInformationHistory informationHistory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "general_information_workflow_id")
    private WorkflowConfig informationWorkflow;

    @Column(name = "business_code")
    private String businessCode;

    private boolean completed = false;
}
