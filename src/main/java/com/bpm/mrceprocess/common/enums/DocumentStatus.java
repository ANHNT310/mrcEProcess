package com.bpm.mrceprocess.common.enums;

import com.bpm.dtos.SelectItem;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum DocumentStatus {

    VALID("Hiệu lực / Valid"),
    INVALID("Không hiệu lực / Invalid");
    private final String description;

    public static List<SelectItem> toSelectItemList() {
        return Arrays.stream(values())
                .map(item -> new SelectItem(item.name(), item.getDescription()))
                .collect(Collectors.toList());}
}
