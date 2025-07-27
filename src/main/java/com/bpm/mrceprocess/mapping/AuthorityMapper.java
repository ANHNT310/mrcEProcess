package com.bpm.mrceprocess.mapping;

import com.bpm.mrceprocess.common.dtos.AuthorityDTO;
import com.bpm.mrceprocess.persistence.entity.Authority;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface AuthorityMapper {
    Authority toEntity(AuthorityDTO authorityDTO);

    AuthorityDTO toDto(Authority authority);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Authority partialUpdate(AuthorityDTO authorityDTO, @MappingTarget Authority authority);
}