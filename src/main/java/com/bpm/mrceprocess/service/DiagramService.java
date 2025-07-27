package com.bpm.mrceprocess.service;

import com.bpm.mrceprocess.common.dtos.DiagramStepParserItemDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DiagramService {

    List<DiagramStepParserItemDTO> parserDocx(MultipartFile file);
}
