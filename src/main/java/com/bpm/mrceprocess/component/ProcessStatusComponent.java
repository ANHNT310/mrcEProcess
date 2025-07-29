package com.bpm.mrceprocess.component;

import com.bpm.mrceprocess.persistence.entity.GeneralInformationHistory;
import com.bpm.mrceprocess.persistence.repository.GeneralInformationHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProcessStatusComponent {

    private final GeneralInformationHistoryRepository informationHistoryRepository;

    public void updateStatus (GeneralInformationHistory informationHistory, String taskName) {

    }
}
