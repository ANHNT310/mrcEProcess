package com.bpm.mrceprocess.mapping;

import com.bpm.mrceprocess.common.dtos.GeneralInformationViewDTO;
import com.bpm.mrceprocess.persistence.entity.GeneralInformationView;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface GeneralInformationViewMapper {
    GeneralInformationView toEntity(GeneralInformationViewDTO generalInformationViewDTO);

    GeneralInformationViewDTO toDto(GeneralInformationView generalInformationView);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    GeneralInformationView partialUpdate(GeneralInformationViewDTO generalInformationViewDTO, @MappingTarget GeneralInformationView generalInformationView);
}