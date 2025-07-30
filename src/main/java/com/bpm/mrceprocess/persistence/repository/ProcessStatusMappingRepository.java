package com.bpm.mrceprocess.persistence.repository;

import com.bpm.mrceprocess.persistence.entity.ProcessStatusMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProcessStatusMappingRepository extends JpaRepository<ProcessStatusMapping, Integer> {

    Optional<ProcessStatusMapping> findByScopeAndDefaultStatus(String scope, boolean defaultStatus);

    Optional<ProcessStatusMapping> findByScopeAndTaskName(String scope, String taskName);
}
