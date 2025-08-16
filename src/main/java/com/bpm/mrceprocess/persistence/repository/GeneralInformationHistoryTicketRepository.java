package com.bpm.mrceprocess.persistence.repository;

import com.bpm.mrceprocess.persistence.entity.GeneralInformationHistoryTicket;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface GeneralInformationHistoryTicketRepository extends Repository<GeneralInformationHistoryTicket, String>,
        JpaSpecificationExecutor<GeneralInformationHistoryTicket> {

    Optional<GeneralInformationHistoryTicket> findByBusinessCode(String businessCode);
}