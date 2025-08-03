package com.bpm.mrceprocess.service.impl;

import com.bpm.mrceprocess.common.dtos.ProcessScopeConfigDTO;
import com.bpm.mrceprocess.mapping.ProcessScopeConfigMapper;
import com.bpm.mrceprocess.persistence.entity.ProcessScopeConfig;
import com.bpm.mrceprocess.persistence.repository.GeneralInformationScopeConfigRepository;
import com.bpm.mrceprocess.service.ProcessScopeConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProcessScopeConfigServiceImpl extends AbstractCrudService<ProcessScopeConfig, ProcessScopeConfigDTO, String>
        implements ProcessScopeConfigService {

    private final GeneralInformationScopeConfigRepository repository;
    private final ProcessScopeConfigMapper mapper;

    @Override
    protected JpaRepository<ProcessScopeConfig, String> getRepository() {
        return repository;
    }

    @Override
    protected ProcessScopeConfig toEntity(ProcessScopeConfigDTO dto) {
        return mapper.toEntity(dto);
    }

    @Override
    protected ProcessScopeConfigDTO toDTO(ProcessScopeConfig entity) {
        return mapper.toDto(entity);
    }

    @Override
    protected ProcessScopeConfig toUpdateEntity(ProcessScopeConfig entity, ProcessScopeConfigDTO dto) {
        return mapper.partialUpdate(dto, entity);
    }
}
