package com.bpm.mrceprocess.persistence.repository;

import com.bpm.mrceprocess.common.enums.ProcessScopeEnum;
import com.bpm.mrceprocess.persistence.entity.GeneralInformation;
import com.bpm.mrceprocess.persistence.entity.GeneralInformationHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public interface GeneralInformationRepository extends JpaRepository<GeneralInformation, String>, JpaSpecificationExecutor<GeneralInformation> {

    int countByCodeIsNotNull();

    Optional<GeneralInformation> findByAvailable(GeneralInformationHistory available);
}