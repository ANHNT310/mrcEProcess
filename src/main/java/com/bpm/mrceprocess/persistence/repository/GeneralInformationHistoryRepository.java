package com.bpm.mrceprocess.persistence.repository;

import com.bpm.mrceprocess.persistence.entity.GeneralInformationHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GeneralInformationHistoryRepository extends JpaRepository<GeneralInformationHistory, String> {

    Page<GeneralInformationHistory> findByBusinessCodeIn(List<String> businessCodes, Pageable pageable);

    Optional<GeneralInformationHistory> findByBusinessCode(String businessCode);
}