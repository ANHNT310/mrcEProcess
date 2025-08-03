package com.bpm.mrceprocess.mapping;

import com.bpm.mrceprocess.persistence.entity.ProcessScopeConfig;
import com.bpm.mrceprocess.persistence.repository.ProcessScopeConfigRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MappingHelper {

    private final ProcessScopeConfigRepository processScopeConfigRepository;

    @Named("stringToProcessScopeConfig")
    public ProcessScopeConfig stringToProcessScopeConfig(String id) {
        if (id == null) {
            return null;
        }
        return processScopeConfigRepository.findById(id).orElse(null);
    }
}
