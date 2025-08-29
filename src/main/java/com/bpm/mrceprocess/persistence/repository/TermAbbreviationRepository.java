package com.bpm.mrceprocess.persistence.repository;

import com.bpm.mrceprocess.persistence.entity.TermAbbreviation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TermAbbreviationRepository extends JpaRepository<TermAbbreviation, String> {

    @Query("SELECT t FROM TermAbbreviation t WHERE t.generalInformationHistory.id = :historyId")
    List<TermAbbreviation> findByGeneralInformationHistoryId(@Param("historyId") String historyId);
}