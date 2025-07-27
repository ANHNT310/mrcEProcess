package com.bpm.mrceprocess.external.payload;

public class WorkflowStartPayloadDTO {

    public record Request (String name,
                           Variable variables){

        public record Variable (String eProcessId) {}
    }

    public record Response(String processInstanceId,
                          String processDefinitionId,
                          String businessKey){}
}
