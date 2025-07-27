package com.bpm.mrceprocess.controller;

import com.bpm.dtos.BaseResponse;
import com.bpm.mrceprocess.common.dtos.DiagramStepParserItemDTO;
import com.bpm.mrceprocess.service.DiagramService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/diagram")
@RequiredArgsConstructor
@Tag(name = "Diagram Utilities", description = "APIs for processing process diagrams")
public class DiagramController {

    private final DiagramService diagramService;

    @PostMapping(value = "/parser-docx", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Parse a DOCX file", description = "Uploads a DOCX file containing a process diagram in a table and parses its steps.")
    @ApiResponse(responseCode = "200", description = "File parsed successfully")
    @ApiResponse(responseCode = "400", description = "Invalid file or format", content = @Content)
    public BaseResponse<List<DiagramStepParserItemDTO>> parseDocx(@RequestParam("file") MultipartFile file) {
        return BaseResponse.success(diagramService.parserDocx(file));
    }
}