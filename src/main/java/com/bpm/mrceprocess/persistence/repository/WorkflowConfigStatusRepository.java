package com.bpm.mrceprocess.persistence.repository;

import com.bpm.mrceprocess.persistence.entity.WorkflowConfigStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface WorkflowConfigStatusRepository extends JpaRepository<WorkflowConfigStatus, String>,
        JpaSpecificationExecutor<WorkflowConfigStatus> {

    Optional<WorkflowConfigStatus> findByTaskName(String taskName);
}