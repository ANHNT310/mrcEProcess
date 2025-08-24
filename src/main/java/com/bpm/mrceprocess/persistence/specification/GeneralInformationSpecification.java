package com.bpm.mrceprocess.persistence.specification;

import com.bpm.dtos.LazyLoadEventDTO;
import com.bpm.mrceprocess.persistence.entity.GeneralInformation;
import com.bpm.mrceprocess.persistence.entity.GeneralInformationHistory;
import com.bpm.utils.FilterSpecification;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public final class GeneralInformationSpecification {

    public static Specification<GeneralInformation> filterAvailableAndByType(String type, LazyLoadEventDTO eventDTO) {
        // This specification handles mandatory conditions and the special global filter logic.
        Specification<GeneralInformation> customSpec = (root, query, cb) -> {
            // Use a LEFT JOIN. The isNotNull predicate will effectively make it an INNER JOIN for the final result set.
            Join<GeneralInformation, GeneralInformationHistory> availableJoin = root.join("available", JoinType.LEFT);

            // 1. Base predicates: filter by type and ensure an available version exists.
            Predicate typePredicate = cb.equal(root.get("type"), type);
            Predicate availableNotNullPredicate = cb.isNotNull(root.get("available"));
            Predicate finalPredicate = cb.and(typePredicate, availableNotNullPredicate);

            // 2. Global filter: search across fields of GeneralInformation and the joined 'available' history.
            String globalFilter = eventDTO.getGlobalFilter();
            if (StringUtils.hasText(globalFilter)) {
                String pattern = "%" + globalFilter.toLowerCase().trim() + "%";

                Predicate globalFilterPredicate = cb.or(
                        // Search in GeneralInformation's own fields
                        cb.like(cb.lower(root.get("name")), pattern),
                        cb.like(cb.lower(root.get("code")), pattern),
                        // Search in the joined 'available' GeneralInformationHistory fields
                        cb.like(cb.lower(availableJoin.get("name")), pattern),
                        cb.like(cb.lower(availableJoin.get("shortDescription")), pattern)
                );
                finalPredicate = cb.and(finalPredicate, globalFilterPredicate);
            }
            return finalPredicate;
        };

        // The generic FilterSpecification handles column-specific filters from the 'filters' map.
        // We create a new DTO without the globalFilter to prevent the generic spec from processing it,
        // as we have already implemented custom logic for it.
        LazyLoadEventDTO columnFiltersOnlyDTO = new LazyLoadEventDTO();
        columnFiltersOnlyDTO.setFilters(eventDTO.getFilters());
        // Sorting information must also be preserved.
        columnFiltersOnlyDTO.setSortField(eventDTO.getSortField());
        columnFiltersOnlyDTO.setSortOrder(eventDTO.getSortOrder());

        Specification<GeneralInformation> columnFilterSpec = new FilterSpecification<>(columnFiltersOnlyDTO);

        // Combine our custom specification with the one for column filters.
        return customSpec.and(columnFilterSpec);
    }
}
