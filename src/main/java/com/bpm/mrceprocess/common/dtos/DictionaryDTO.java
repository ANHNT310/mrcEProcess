package com.bpm.mrceprocess.common.dtos;

import lombok.Value;

import java.io.Serializable;

@Value
public class DictionaryDTO implements Serializable {
    String id;
    String text;
    String vieDefinition;
    String enDefinition;
}