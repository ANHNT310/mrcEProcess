package com.bpm.mrceprocess.mapping;

import com.bpm.mrceprocess.common.dtos.GeneralInformationDTO;
import com.bpm.mrceprocess.persistence.entity.GeneralInformation;
import com.bpm.mrceprocess.persistence.entity.GeneralInformationHistory;
import org.mapstruct.*;

import java.util.Collection;
import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface GeneralInformationMapper {

    @Mapping(target = "scope", ignore = true)
    GeneralInformation toEntity(GeneralInformationDTO generalInformationDto);

    @Mapping(target = "availableId", source = "available.id")
    @Mapping(target = "scope", source = "scope.name")
    GeneralInformationDTO toDto(GeneralInformation generalInformation);

    List<GeneralInformationDTO> toDto (Collection<GeneralInformation> generalInfos);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "scope", ignore = true)
    GeneralInformation partialUpdate(GeneralInformationDTO generalInformationDto, @MappingTarget GeneralInformation generalInformation);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    GeneralInformation partialUpdate(GeneralInformation generalInformationDto, @MappingTarget GeneralInformation generalInformation);
}