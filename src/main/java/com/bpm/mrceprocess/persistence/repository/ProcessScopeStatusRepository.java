package com.bpm.mrceprocess.persistence.repository;

import com.bpm.mrceprocess.persistence.entity.ProcessScopeStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessScopeStatusRepository extends JpaRepository<ProcessScopeStatus, String> {
}