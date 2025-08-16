package com.bpm.mrceprocess.persistence.repository;

import com.bpm.mrceprocess.persistence.entity.WorkflowConfigStatus;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.Repository;

public interface WorkflowConfigStatusRepository extends Repository<WorkflowConfigStatus, String>,
        JpaSpecificationExecutor<WorkflowConfigStatus> {
}