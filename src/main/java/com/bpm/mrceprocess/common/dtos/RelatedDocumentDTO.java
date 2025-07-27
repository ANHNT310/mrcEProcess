package com.bpm.mrceprocess.common.dtos;

import lombok.Value;

import java.io.Serializable;
import java.util.List;
import java.util.Set;


@Value
public class RelatedDocumentDTO implements Serializable {
    String id;
    List<String> legalProvisions;
    List<String> internalDocuments;
    List<String> relatedProcesses;
    Set<RelatedDocumentTemplateDTO> templates;
}