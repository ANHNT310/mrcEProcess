package com.bpm.mrceprocess.persistence.specification;

import com.bpm.dtos.LazyLoadEventDTO;
import com.bpm.mrceprocess.persistence.entity.GeneralInformationHistory;
import com.bpm.utils.FilterSpecification;
import org.springframework.data.jpa.domain.Specification;

public final class GeneralInformationHistorySpecification {

    public static Specification<GeneralInformationHistory> filterByCreateBy(String userId, LazyLoadEventDTO eventDTO) {
        Specification<GeneralInformationHistory> userSpec = (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("createdBy"), userId);

        Specification<GeneralInformationHistory> filterSpec = new FilterSpecification<>(eventDTO);

        return userSpec.and(filterSpec);
    }
}
