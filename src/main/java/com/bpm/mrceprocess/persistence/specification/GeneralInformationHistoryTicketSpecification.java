package com.bpm.mrceprocess.persistence.specification;

import com.bpm.mrceprocess.persistence.entity.GeneralInformationHistory;
import com.bpm.mrceprocess.persistence.entity.GeneralInformationHistoryTicket;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Utility class for creating reusable Specifications for the {@link GeneralInformationHistoryTicket} entity.
 */
public final class GeneralInformationHistoryTicketSpecification {

    private GeneralInformationHistoryTicketSpecification() {
        // Private constructor to prevent instantiation.
    }

    /**
     * Creates a Specification that first filters by a list of business codes,
     * and then applies a global keyword search on top of that result.
     *
     * @param businessCodes The list of business codes that forms the base of the search.
     * @param keyword       The global filter keyword to search across multiple fields. Can be null or empty.
     * @return A combined Specification for GeneralInformationHistoryTicket.
     */
    public static Specification<GeneralInformationHistoryTicket> createFilter(List<String> businessCodes, String keyword) {
        return (root, query, cb) -> {
            // Add a fetch join to eagerly load the related GeneralInformationHistory.
            // This prevents the N+1 query problem when mapping the results.
            // This should only be applied to the main query, not the count query.
            assert query != null;
            if (query.getResultType() != Long.class && query.getResultType() != long.class) {
                root.fetch("informationHistory", JoinType.LEFT);
                query.distinct(true);
            }

            // Step 1: The initial filter by businessCodes is the base requirement.
            // If the list is empty, no results should be returned as per the logic "first filter by...".
            if (CollectionUtils.isEmpty(businessCodes)) {
                return cb.disjunction(); // Returns a predicate that is always false.
            }

            Predicate basePredicate = root.get("businessCode").in(businessCodes);

            // Step 2: If there's no keyword, we are done with just the base filter.
            if (!StringUtils.hasText(keyword)) {
                return basePredicate;
            }

            // Step 3: If a keyword exists, create the multi-column search predicate.
            String pattern = "%" + keyword.toLowerCase().trim() + "%";
            Join<GeneralInformationHistoryTicket, GeneralInformationHistory> historyJoin = root.join("informationHistory");

            Predicate keywordPredicate = cb.or(
                    cb.like(cb.lower(root.get("businessCode")), pattern),
                    cb.like(cb.lower(historyJoin.get("name")), pattern),
                    cb.like(cb.lower(historyJoin.get("shortDescription")), pattern)
            );

            // Step 4: Combine the base filter and the keyword filter with AND.
            return cb.and(basePredicate, keywordPredicate);
        };
    }
}
