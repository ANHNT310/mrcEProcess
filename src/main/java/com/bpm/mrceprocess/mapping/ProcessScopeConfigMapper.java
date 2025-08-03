package com.bpm.mrceprocess.mapping;

import com.bpm.mrceprocess.common.dtos.ProcessScopeConfigDTO;
import com.bpm.mrceprocess.persistence.entity.ProcessScopeStatus;
import com.bpm.mrceprocess.persistence.entity.ProcessScopeConfig;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProcessScopeConfigMapper {

    ProcessScopeConfig toEntity(ProcessScopeConfigDTO processScopeConfigDTO);

    ProcessScopeConfigDTO toDto(ProcessScopeConfig processScopeConfig);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ProcessScopeConfig partialUpdate(ProcessScopeConfigDTO processScopeConfigDTO, @MappingTarget ProcessScopeConfig processScopeConfig);

    /**
     * After mapping from DTO to entity, this method is called to establish the
     * bidirectional relationship between ProcessScopeConfig and its ProcessScopeStatus children.
     * It ensures that each child status has a reference back to its parent scope.
     * @param entity The target ProcessScopeConfig entity after mapping.
     */
    @AfterMapping
    default void establishBidirectionalRelationships(@MappingTarget ProcessScopeConfig entity) {
        if (entity.getStatuses() != null) {
            entity.getStatuses().forEach(status -> status.setScope(entity));
        }
    }
}