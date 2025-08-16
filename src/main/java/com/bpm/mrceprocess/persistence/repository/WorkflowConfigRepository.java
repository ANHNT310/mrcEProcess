package com.bpm.mrceprocess.persistence.repository;

import com.bpm.mrceprocess.common.enums.GeneralInformationType;
import com.bpm.mrceprocess.persistence.entity.WorkflowConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface WorkflowConfigRepository extends JpaRepository<WorkflowConfig, String>,
        JpaSpecificationExecutor<WorkflowConfig> {
}
