package com.bpm.mrceprocess.mapping;

import com.bpm.mrceprocess.common.dtos.ViewProcessDTO;
import com.bpm.mrceprocess.persistence.entity.GeneralInformation;
import com.bpm.mrceprocess.persistence.entity.GeneralInformationHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {MappingHelper.class})
public interface ViewProcessDTOMapper {

    @Mapping(target = "version", source = "available.version")
    @Mapping(target = "historyCreatedBy", source = "available.createdBy")
    @Mapping(target = "historyCreatedAt", source = "available.createdAt")
    @Mapping(target = "shortDescription", source = "available.shortDescription")
    @Mapping(target = "name", source = "available.name")
    @Mapping(target = "generalId", source = "id")
    @Mapping(target = "historyId", source = "available.id")
    ViewProcessDTO toDTO (GeneralInformation general);

    @Mapping(target = "historyId", source = "id")
    @Mapping(target = "historyCreatedBy", source = "createdBy")
    @Mapping(target = "historyCreatedAt", source = "createdAt")
    @Mapping(target = "generalId", source = "generalInformation.id")
    @Mapping(target = "code", source = "generalInformation.code")
    ViewProcessDTO toDTO (GeneralInformationHistory history);

    List<ViewProcessDTO> toDTOs (List<GeneralInformation> generals);
}
