package com.bpm.mrceprocess.component;

import com.bpm.mrceprocess.persistence.entity.GeneralInformationHistory;
import com.bpm.mrceprocess.persistence.entity.ProcessStatusMapping;
import com.bpm.mrceprocess.persistence.repository.GeneralInformationHistoryRepository;
import com.bpm.mrceprocess.persistence.repository.ProcessStatusMappingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProcessStatusComponent {

    private final GeneralInformationHistoryRepository informationHistoryRepository;
    private final ProcessStatusMappingRepository processStatusMappingRepository;

    public void updateStatus (GeneralInformationHistory informationHistory, String taskName) {
        String scope = informationHistory.getGeneralInformation().getScope().name();
        ProcessStatusMapping processStatus = processStatusMappingRepository.findByScopeAndTaskName(scope, taskName)
                .orElse(null);
        if (processStatus != null) {
            informationHistory.setStatus(processStatus);
            informationHistoryRepository.save(informationHistory);
        }
    }
}
