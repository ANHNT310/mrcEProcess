package com.bpm.mrceprocess.persistence.repository;

import com.bpm.mrceprocess.persistence.entity.GeneralInformationHistoryTicket;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.Repository;

public interface GeneralInformationHistoryTicketRepository extends Repository<GeneralInformationHistoryTicket, String>,
        JpaSpecificationExecutor<GeneralInformationHistoryTicket> {
}