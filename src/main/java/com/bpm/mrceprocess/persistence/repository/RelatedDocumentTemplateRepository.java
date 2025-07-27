package com.bpm.mrceprocess.persistence.repository;

import com.bpm.mrceprocess.persistence.entity.RelatedDocumentTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RelatedDocumentTemplateRepository extends JpaRepository<RelatedDocumentTemplate, String> {
}