package com.bpm.mrceprocess.external.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkflowDefinitionResDTO {
    String id;
    String definitionKey;
    String name;
    String code;
    String description;
}
