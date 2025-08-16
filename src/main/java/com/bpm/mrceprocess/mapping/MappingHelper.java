package com.bpm.mrceprocess.mapping;

import com.bpm.mrceprocess.persistence.entity.Category;
import com.bpm.mrceprocess.persistence.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MappingHelper {

    private final CategoryRepository categoryRepository;

    @Named("categoryFromId")
    public Category categoryFromId(String id) {
        if (StringUtils.isEmpty(id)) {
            return null;
        }

        return categoryRepository.findById(id).orElse(null);
    }
}
