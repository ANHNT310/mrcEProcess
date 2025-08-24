package com.bpm.mrceprocess.common.dtos;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeactivateProcessResponseDTO {

    @Schema(description = "The ID of the specific GeneralInformationHistory version that was saved.", example = "c7a7a7e7-7e7e-3f3f-7e7e-7e7e7e7e7e7e")
    private String historyId;

    private String businessKey;
}
