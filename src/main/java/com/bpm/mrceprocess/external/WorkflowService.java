package com.bpm.mrceprocess.external;

import com.bpm.mrceprocess.configuration.FeignClientConfig;
import com.bpm.mrceprocess.external.payload.WorkflowStartPayloadDTO;
import com.bpm.mrceprocess.external.payload.WorkflowTaskSummaryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name = "workflow-service", url = "${app-feign.services.workflow}", path = "/workflow",
        configuration = FeignClientConfig.class)
public interface WorkflowService {

    @PostMapping("/runtime/process-instances/start")
    WorkflowStartPayloadDTO.Response startWorkflow(WorkflowStartPayloadDTO.Request request);

    @GetMapping("/user-tasks/pending/group/{group}")
    List<WorkflowTaskSummaryDTO> pending (@PathVariable("group") String group);
}
