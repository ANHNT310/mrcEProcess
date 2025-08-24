package com.bpm.mrceprocess.persistence.repository;

import com.bpm.mrceprocess.common.enums.GeneralInformationType;
import com.bpm.mrceprocess.common.enums.WorkflowConfigScope;
import com.bpm.mrceprocess.persistence.entity.WorkflowConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface WorkflowConfigRepository extends JpaRepository<WorkflowConfig, String>,
        JpaSpecificationExecutor<WorkflowConfig> {

    List<WorkflowConfig> findByScope(WorkflowConfigScope scope);
}
