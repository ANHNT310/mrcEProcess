package com.bpm.mrceprocess.persistence.repository;

import com.bpm.mrceprocess.persistence.entity.GeneralInformationView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GeneralInformationViewRepository extends JpaRepository<GeneralInformationView, String>,
        JpaSpecificationExecutor<GeneralInformationView> {
}