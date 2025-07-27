package com.bpm.mrceprocess.persistence.repository;

import com.bpm.mrceprocess.persistence.entity.RelatedDocument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RelatedDocumentRepository extends JpaRepository<RelatedDocument, String> {
}