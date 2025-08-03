package com.bpm.mrceprocess.persistence.repository;

import com.bpm.mrceprocess.common.enums.ProcessScopeEnum;
import com.bpm.mrceprocess.persistence.entity.ProcessScopeConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProcessScopeConfigRepository extends JpaRepository<ProcessScopeConfig, String> {

    List<ProcessScopeConfig> findByType(ProcessScopeEnum type);
}
