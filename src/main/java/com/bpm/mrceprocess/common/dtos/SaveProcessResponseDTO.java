package com.bpm.mrceprocess.common.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "SaveProcessResponse", description = "Response containing the IDs of the created/updated process entities.")
public class SaveProcessResponseDTO {

    @Schema(description = "The ID of the parent GeneralInformation entity.", example = "d8b8a8e8-8e8e-4f4f-8e8e-8e8e8e8e8e8e")
    private String generalId;

    @Schema(description = "The ID of the specific GeneralInformationHistory version that was saved.", example = "c7a7a7e7-7e7e-3f3f-7e7e-7e7e7e7e7e7e")
    private String historyId;
}