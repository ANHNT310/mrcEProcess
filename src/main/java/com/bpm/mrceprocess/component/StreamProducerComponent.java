package com.bpm.mrceprocess.component;

import com.bpm.mrceprocess.common.dtos.WorkflowServiceRegisterEventDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StreamProducerComponent {

    private final StreamBridge streamBridge;

    public void sendServiceRegisterWorkflowEvent(WorkflowServiceRegisterEventDTO payload) {
        streamBridge.send("serviceRegisterEventProducer-out-0", MessageBuilder
                .withPayload(payload)
                .build());
    }
}
