package com.bpm.mrceprocess.persistence.repository;

import com.bpm.mrceprocess.persistence.entity.TermAbbreviation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TermAbbreviationRepository extends JpaRepository<TermAbbreviation, String> {
}