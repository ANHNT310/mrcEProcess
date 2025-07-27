package com.bpm.mrceprocess.service.impl;

import com.bpm.mrceprocess.common.dtos.DiagramStepParserItemDTO;
import com.bpm.mrceprocess.external.DocumentService;
import com.bpm.mrceprocess.service.DiagramService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DiagramServiceImpl implements DiagramService {

    private final DocumentService documentService;

    private static final int STEP_INDEX = 0;
    private static final int ACTOR_INDEX = 1;
    private static final int DESCRIPTION_INDEX = 2;
    private static final int SLA_INDEX = 3;
    private static final int DOCUMENT_REFERENCE_INDEX = 4;

    @Override
    public List<DiagramStepParserItemDTO> parserDocx(MultipartFile file) {
        List<Map<String, String>> parsers = documentService.docxTableParser(file);

        if (parsers == null) {
            return Collections.emptyList();
        }

        return parsers.stream()
                .map(parserMap -> new ArrayList<>(parserMap.values()))
                .map(this::createDtoFromRow)
                .toList();
    }

    private DiagramStepParserItemDTO createDtoFromRow(List<String> row) {
        DiagramStepParserItemDTO dto = new DiagramStepParserItemDTO();
        dto.setKey(getValueAtIndex(row, STEP_INDEX).trim());
        dto.setStep(getValueAtIndex(row, STEP_INDEX));
        dto.setResponsibility(getValueAtIndex(row, ACTOR_INDEX));
        dto.setDescription(getValueAtIndex(row, DESCRIPTION_INDEX));
        dto.setSla(getValueAtIndex(row, SLA_INDEX));
        dto.setReferenceDocument(getValueAtIndex(row, DOCUMENT_REFERENCE_INDEX));
        return dto;
    }

    private String getValueAtIndex(List<String> list, int index) {
        return (list != null && index < list.size()) ? list.get(index) : null;
    }
}
