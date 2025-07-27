package com.bpm.mrceprocess.persistence.repository;

import com.bpm.mrceprocess.persistence.entity.DocumentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DocumentTypeRepository extends JpaRepository<DocumentType, String>, JpaSpecificationExecutor<DocumentType> {
}