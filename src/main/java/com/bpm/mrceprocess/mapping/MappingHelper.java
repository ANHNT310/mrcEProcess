package com.bpm.mrceprocess.mapping;

import com.bpm.mrceprocess.persistence.entity.Category;
import com.bpm.mrceprocess.persistence.entity.ProcessScopeConfig;
import com.bpm.mrceprocess.persistence.repository.CategoryRepository;
import com.bpm.mrceprocess.persistence.repository.ProcessScopeConfigRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MappingHelper {

    private final ProcessScopeConfigRepository processScopeConfigRepository;
    private final CategoryRepository categoryRepository;

    @Named("stringToProcessScopeConfig")
    public ProcessScopeConfig stringToProcessScopeConfig(String id) {
        if (id == null) {
            return null;
        }
        return processScopeConfigRepository.findById(id).orElse(null);
    }

    @Named("categoryFromId")
    public Category categoryFromId(String id) {
        if (StringUtils.isEmpty(id)) {
            return null;
        }

        return categoryRepository.findById(id).orElse(null);
    }
}
