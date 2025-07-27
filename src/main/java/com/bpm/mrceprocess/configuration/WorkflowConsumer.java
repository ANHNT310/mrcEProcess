package com.bpm.mrceprocess.configuration;

import com.bpm.mrceprocess.common.dtos.ProcessCanceledEventDTO;
import com.bpm.mrceprocess.service.ProcessActionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.util.function.Consumer;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class WorkflowConsumer {

    private final ProcessActionService processActionService;

    @Bean("workflowCanceledConsumer")
    public Consumer<Flux<ProcessCanceledEventDTO>> workflowCanceledConsumer() {
        return flux -> flux
                .doOnNext(event -> log.info("workflowCanceledConsumer Event: {}", event))
                .doOnNext(processActionService::workflowCanceled)
                .subscribe();
    }

}
