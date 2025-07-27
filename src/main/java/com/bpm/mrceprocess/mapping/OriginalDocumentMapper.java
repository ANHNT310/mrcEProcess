package com.bpm.mrceprocess.mapping;

import com.bpm.mrceprocess.common.dtos.CreateProcessRequestDTO;
import com.bpm.mrceprocess.common.dtos.OriginalDocumentDTO;
import com.bpm.mrceprocess.common.dtos.UpdateProcessRequestDTO;
import com.bpm.mrceprocess.persistence.entity.OriginalDocument;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface OriginalDocumentMapper {
    OriginalDocument toEntity(OriginalDocumentDTO originalDocumentDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "code", target = "code")
    @Mapping(source = "subject", target = "subject")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "type", target = "type")
    @Mapping(source = "issuedDate", target = "issuedDate")
    @Mapping(source = "approvalAuthority", target = "approvalAuthority")
    @Mapping(source = "effectiveType", target = "effectiveType")
    @Mapping(source = "effectiveDuration", target = "duration")
    @Mapping(source = "effectiveDate", target = "effectiveDate")
    @Mapping(source = "endDate", target = "endDate")
    @Mapping(source = "shareFor", target = "shareFor")
    @Mapping(source = "documentLink", target = "documentLink")
    @Mapping(source = "attachmentId", target = "attachmentId")
    @Mapping(target = "specificUnits", source = "specificUnits")
    @Mapping(target = "specificUsers", source = "specificUsers")
    @Mapping(target = "relevantUnits", source = "relevantUnits")
    @Mapping(target = "effectivenessSame", source = "effectivenessSame")
    OriginalDocument toEntity (CreateProcessRequestDTO.Request.Information.OriginalDocument originalDocument);

    List<OriginalDocument> toEntities (List<CreateProcessRequestDTO.Request.Information.OriginalDocument> originalDocuments);

    // --- UPDATED: Added explicit mappings for consistency and correctness ---
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "code", target = "code")
    @Mapping(source = "subject", target = "subject")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "type", target = "type")
    @Mapping(source = "issuedDate", target = "issuedDate")
    @Mapping(source = "approvalAuthority", target = "approvalAuthority")
    @Mapping(source = "effectiveType", target = "effectiveType")
    @Mapping(source = "effectiveDuration", target = "duration")
    @Mapping(source = "effectiveDate", target = "effectiveDate")
    @Mapping(source = "endDate", target = "endDate")
    @Mapping(source = "shareFor", target = "shareFor")
    @Mapping(source = "documentLink", target = "documentLink")
    @Mapping(source = "attachmentId", target = "attachmentId")
    @Mapping(target = "specificUnits", source = "specificUnits")
    @Mapping(target = "specificUsers", source = "specificUsers")
    @Mapping(target = "relevantUnits", source = "relevantUnits")
    @Mapping(target = "effectivenessSame", source = "effectivenessSame")
    OriginalDocument toEntity (UpdateProcessRequestDTO.Request.Information.OriginalDocument originalDocument);

    @Mapping(target = "effectivenessSame", source = "effectivenessSame")
    OriginalDocumentDTO toDto(OriginalDocument originalDocument);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    OriginalDocument partialUpdate(OriginalDocumentDTO originalDocumentDTO, @MappingTarget OriginalDocument originalDocument);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    OriginalDocument partialUpdate(UpdateProcessRequestDTO.Request.Information.OriginalDocument originalDocumentDTO,
                                   @MappingTarget OriginalDocument originalDocument);
}