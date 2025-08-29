package com.bpm.mrceprocess.persistence.repository;

import com.bpm.mrceprocess.persistence.entity.ProcessScopeConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GeneralInformationScopeConfigRepository extends JpaRepository<ProcessScopeConfig, String>,
        JpaSpecificationExecutor<ProcessScopeConfig> {
}