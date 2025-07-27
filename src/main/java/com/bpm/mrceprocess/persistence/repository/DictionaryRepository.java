package com.bpm.mrceprocess.persistence.repository;

import com.bpm.mrceprocess.persistence.entity.Dictionary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DictionaryRepository extends JpaRepository<Dictionary, String> {

    @Query("SELECT d FROM Dictionary d WHERE " +
            "LOWER(d.vieDefinition) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(d.enDefinition) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Dictionary> searchByDefinitions(@Param("keyword") String keyword);

    List<Dictionary> findByText(String text);

}