package com.bpm.mrceprocess.common.dtos;

public class SubmitDeactivateProcessDTO {

    public record Request(String generalId) {}

    public record Response(String historyId, String businessKey) {}
}
