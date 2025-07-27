package com.bpm.mrceprocess.common.dtos;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ProcessDetailDTO {

    private GeneralInformationHistoryDTO informationHistory;
    private GeneralInformationDTO generalInformation;
    private List<OriginalDocumentDTO> processDocuments;
    private List<DiagramDescriptionDTO> diagramDescriptions;
    private List<TermAbbreviationDTO> termAbbreviations;
    private RelatedDocumentDTO relatedDocuments;
}
