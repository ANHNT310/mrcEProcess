package com.bpm.mrceprocess.mapping;

import com.bpm.mrceprocess.common.dtos.DictionaryDTO;
import com.bpm.mrceprocess.persistence.entity.Dictionary;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface DictionaryMapper {
    Dictionary toEntity(DictionaryDTO dictionaryDTO);

    DictionaryDTO toDto(Dictionary dictionary);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Dictionary partialUpdate(DictionaryDTO dictionaryDTO, @MappingTarget Dictionary dictionary);
}