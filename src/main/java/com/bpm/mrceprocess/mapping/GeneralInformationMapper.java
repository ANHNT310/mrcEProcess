package com.bpm.mrceprocess.mapping;

import com.bpm.mrceprocess.common.dtos.GeneralInformationDTO;
import com.bpm.mrceprocess.persistence.entity.GeneralInformation;
import org.mapstruct.*;

import java.util.Collection;
import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface GeneralInformationMapper {
    GeneralInformation toEntity(GeneralInformationDTO generalInformationDto);

    @Mapping(target = "availableId", expression = "java(getAvailableId(generalInformation))")
    GeneralInformationDTO toDto(GeneralInformation generalInformation);

    List<GeneralInformationDTO> toDto (Collection<GeneralInformation> generalInfos);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    GeneralInformation partialUpdate(GeneralInformationDTO generalInformationDto, @MappingTarget GeneralInformation generalInformation);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    GeneralInformation partialUpdate(GeneralInformation generalInformationDto, @MappingTarget GeneralInformation generalInformation);

    default String getAvailableId(GeneralInformation generalInformation) {
        if (generalInformation.getAvailable() != null) {
            return generalInformation.getAvailable().getId();
        }
        return null;
    }
}