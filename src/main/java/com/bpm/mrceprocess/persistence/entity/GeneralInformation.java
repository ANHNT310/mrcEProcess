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
@Table(name = "general_information")
public class GeneralInformation extends AuditorProvider {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String code;

    private String name;

    private String shortDescription;

    @Enumerated(EnumType.STRING)
    private ProcessScopeEnum scope;

    @OneToMany(mappedBy = "generalInformation", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<GeneralInformationHistory> histories = new HashSet<>();

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "available_id", referencedColumnName = "id")
    private GeneralInformationHistory available;

    public void addHistory(GeneralInformationHistory history) {
        histories.add(history);
        history.setGeneralInformation(this);
    }

    public void removeHistory(GeneralInformationHistory history) {
        histories.remove(history);
        history.setGeneralInformation(null);
    }
}
