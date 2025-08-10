package com.bpm.mrceprocess.startup;

import com.bpm.mrceprocess.common.consts.ApplicationConst;
import com.bpm.mrceprocess.common.dtos.WorkflowServiceRegisterEventDTO;
import com.bpm.mrceprocess.component.StreamProducerComponent;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InstServiceWorkflowRunner {

    private final StreamProducerComponent streamProducerComponent;

    @EventListener(ApplicationReadyEvent.class)
    public void run() {
        WorkflowServiceRegisterEventDTO eventDTO = new WorkflowServiceRegisterEventDTO(
                ApplicationConst.WORKFLOW_TENANT,
                ApplicationConst.WORKFLOW_TENANT,
                ApplicationConst.WORKFLOW_TENANT);
        streamProducerComponent.sendServiceRegisterWorkflowEvent(eventDTO);
    }
}
