package com.bpm.mrceprocess.common.dtos;

import lombok.Data;

@Data
public class DiagramStepParserItemDTO {

    private String key;

    private String step;

    private String description;

    private String responsibility;

    private String sla;

    private String referenceDocument;
}
