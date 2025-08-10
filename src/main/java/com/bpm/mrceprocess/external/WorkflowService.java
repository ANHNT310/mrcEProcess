package com.bpm.mrceprocess.external;

import com.bpm.mrceprocess.configuration.FeignClientConfig;
import com.bpm.mrceprocess.external.payload.WorkflowStartPayloadResDTO;
import com.bpm.mrceprocess.external.payload.WorkflowTaskSummaryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@FeignClient(name = "workflow-service", url = "${app-feign.services.workflow}", path = "/workflow",
        configuration = FeignClientConfig.class)
public interface WorkflowService {

    @PostMapping("/runtime/process-instances/start/{process}")
    WorkflowStartPayloadResDTO startWorkflow(@PathVariable String process, @RequestBody Map<String, Object> request);

    @GetMapping("/user-tasks/pending/group/{group}")
    List<WorkflowTaskSummaryDTO> pending (@PathVariable("group") String group);
}
