package com.bpm.mrceprocess.persistence.repository;

import com.bpm.mrceprocess.persistence.entity.ProcessScopeConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessScopeConfigRepository extends JpaRepository<ProcessScopeConfig, String> {
}
