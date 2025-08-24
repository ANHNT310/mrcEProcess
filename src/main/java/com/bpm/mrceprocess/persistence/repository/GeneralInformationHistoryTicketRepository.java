package com.bpm.mrceprocess.persistence.repository;

import com.bpm.mrceprocess.persistence.entity.GeneralInformationHistoryTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface GeneralInformationHistoryTicketRepository extends JpaRepository<GeneralInformationHistoryTicket, String>,
        JpaSpecificationExecutor<GeneralInformationHistoryTicket> {

    Optional<GeneralInformationHistoryTicket> findByBusinessCode(String businessCode);

    List<GeneralInformationHistoryTicket> findByBusinessCodeIn(List<String> businessCodes);
}