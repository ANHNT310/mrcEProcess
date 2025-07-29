package com.bpm.mrceprocess.common.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserTaskCreatedEventDTO {

    private String taskId;
    private String taskName;
    private String taskDefinitionKey;
    private List<String> userIds;
    private List<String> groupIds;
    private String businessKey;
}
