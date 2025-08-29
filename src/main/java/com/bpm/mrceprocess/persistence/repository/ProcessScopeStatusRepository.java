package com.bpm.mrceprocess.persistence.repository;

import com.bpm.mrceprocess.persistence.entity.ProcessScopeStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProcessScopeStatusRepository extends JpaRepository<ProcessScopeStatus, String> {

    Optional<ProcessScopeStatus> findByDefaultEnd(boolean defaultEnd);
}