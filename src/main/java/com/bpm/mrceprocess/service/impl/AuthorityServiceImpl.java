package com.bpm.mrceprocess.service.impl;

import com.bpm.mrceprocess.common.dtos.AuthorityDTO;
import com.bpm.mrceprocess.mapping.AuthorityMapper;
import com.bpm.mrceprocess.persistence.entity.Authority;
import com.bpm.mrceprocess.persistence.repository.AuthorityRepository;
import com.bpm.mrceprocess.service.AuthorityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorityServiceImpl extends AbstractCrudService<Authority, AuthorityDTO, String> implements AuthorityService {

    private final AuthorityRepository authorityRepository;
    private final AuthorityMapper authorityMapper;

    @Override
    protected JpaRepository<Authority, String> getRepository() {
        return authorityRepository;
    }

    @Override
    protected Authority toEntity(AuthorityDTO dto) {
        return authorityMapper.toEntity(dto);
    }

    @Override
    protected AuthorityDTO toDTO(Authority entity) {
        return authorityMapper.toDto(entity);
    }

    @Override
    protected Authority toUpdateEntity(Authority entity, AuthorityDTO dto) {
        return authorityMapper.partialUpdate(dto, entity);
    }
}
