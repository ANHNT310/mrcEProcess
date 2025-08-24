package com.bpm.mrceprocess.common.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeactivateProcessRequestDTO {

    private String generalId;
}
