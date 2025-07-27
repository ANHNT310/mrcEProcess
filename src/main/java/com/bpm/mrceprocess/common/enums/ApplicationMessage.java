package com.bpm.mrceprocess.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum ApplicationMessage {
    EMPTY("0", "0"),

    //Prefix code: 150xxx
    WF_TASK_ASSIGNED("130001", "Task already assigned.")
    ;
    private final String code;
    private final String message;

    public static ApplicationMessage from(String code) {
        return Arrays.stream(ApplicationMessage.values())
                .filter(t -> t.name().equals(code))
                .findAny()
                .orElse(EMPTY);
    }
}
