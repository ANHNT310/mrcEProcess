package com.bpm.mrceprocess.persistence.repository;

import com.bpm.mrceprocess.persistence.entity.ProcessDetailInformationView;
import com.bpm.utils.FilterSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ProcessDetailInformationViewRepository extends JpaRepository<ProcessDetailInformationView, String>,
        JpaSpecificationExecutor<ProcessDetailInformationView> {

    Page<ProcessDetailInformationView> findAllByCreatedBy(String createdBy, FilterSpecification<ProcessDetailInformationView> specification, Pageable pageable);
}