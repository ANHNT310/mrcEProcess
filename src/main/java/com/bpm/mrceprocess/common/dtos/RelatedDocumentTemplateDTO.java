package com.bpm.mrceprocess.common.dtos;

import lombok.Value;

import java.io.Serializable;

@Value
public class RelatedDocumentTemplateDTO implements Serializable {
    String id;
    String name;
    String fileId;
}