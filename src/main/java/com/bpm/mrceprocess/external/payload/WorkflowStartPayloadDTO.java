package com.bpm.mrceprocess.external.payload;

import java.util.Map;

public class WorkflowStartPayloadDTO {

    public record Request (String name,
                           Map<String, String> variables){

        public record Variable (String eProcessId) {}
    }

    public record Response(String processInstanceId,
                          String processDefinitionId,
                          String businessKey){}
}
