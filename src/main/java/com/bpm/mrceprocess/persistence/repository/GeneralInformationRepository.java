package com.bpm.mrceprocess.persistence.repository;

import com.bpm.mrceprocess.common.enums.ProcessScopeEnum;
import com.bpm.mrceprocess.persistence.entity.GeneralInformation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface GeneralInformationRepository extends JpaRepository<GeneralInformation, String> {

    @Query("select g from GeneralInformation g where g.createdBy = :createdBy")
    Page<GeneralInformation> findByCreatedBy(String createdBy, Pageable pageable);

    @Query("""
            select g from GeneralInformation g left join g.histories histories
            where g.createdBy = :createdBy or histories.createdBy = :createdBy""")
    Page<GeneralInformation> findByCreatedByOrHistories_CreatedBy(String createdBy, Pageable pageable);

    Page<GeneralInformation> findByScopeAndAvailableNotNull(ProcessScopeEnum scope, Pageable pageable);

    @Query("SELECT COUNT(g) FROM GeneralInformation g WHERE g.available IS NOT NULL AND CAST(g.createdAt AS date) = :targetDate")
    Long countByAvailableIsNotNullAndCreatedAtDate(@Param("targetDate") LocalDate targetDate);

}