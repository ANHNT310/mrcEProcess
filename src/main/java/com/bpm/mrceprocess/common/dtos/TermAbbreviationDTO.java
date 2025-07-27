package com.bpm.mrceprocess.common.dtos;

import lombok.Value;

import java.io.Serializable;


@Value
public class TermAbbreviationDTO implements Serializable {
    String id;
    String vieDefinition;
    String enDefinition;
    String referenceDictionary;
}