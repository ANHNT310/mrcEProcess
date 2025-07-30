package com.bpm.mrceprocess.service.impl;

import com.bpm.mrceprocess.common.dtos.ProcessStatusMappingDTO;
import com.bpm.mrceprocess.mapping.ProcessStatusMappingMapper;
import com.bpm.mrceprocess.persistence.entity.ProcessStatusMapping;
import com.bpm.mrceprocess.persistence.repository.ProcessStatusMappingRepository;
import com.bpm.mrceprocess.service.ProcessStatusMappingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProcessStatusMappingServiceImpl extends AbstractCrudService<ProcessStatusMapping, ProcessStatusMappingDTO, String> implements ProcessStatusMappingService {

    private final ProcessStatusMappingRepository repository;
    private final ProcessStatusMappingMapper mapper;

    @Override
    protected JpaRepository<ProcessStatusMapping, String> getRepository() {
        return repository;
    }

    @Override
    protected ProcessStatusMapping toEntity(ProcessStatusMappingDTO dto) {
        return mapper.toEntity(dto);
    }

    @Override
    protected ProcessStatusMappingDTO toDTO(ProcessStatusMapping entity) {
        return mapper.toDto(entity);
    }

    @Override
    protected ProcessStatusMapping toUpdateEntity(ProcessStatusMapping entity, ProcessStatusMappingDTO dto) {
        return mapper.partialUpdate(dto, entity);
    }
}
