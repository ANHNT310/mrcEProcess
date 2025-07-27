package com.bpm.mrceprocess.persistence.repository;

import com.bpm.mrceprocess.persistence.entity.DiagramDescription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiagramDescriptionRepository extends JpaRepository<DiagramDescription, String> {
}