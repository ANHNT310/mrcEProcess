package com.bpm.mrceprocess.persistence.repository;

import com.bpm.mrceprocess.common.enums.GeneralInformationType;
import com.bpm.mrceprocess.persistence.entity.GeneralInformationWorkflow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface GeneralInformationWorkflowRepository extends JpaRepository<GeneralInformationWorkflow, String>,
        JpaSpecificationExecutor<GeneralInformationWorkflow> {

    Optional<GeneralInformationWorkflow> findByType(GeneralInformationType type);
}
