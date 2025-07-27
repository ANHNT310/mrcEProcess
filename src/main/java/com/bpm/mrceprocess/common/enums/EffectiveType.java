package com.bpm.mrceprocess.common.enums;

import com.bpm.dtos.SelectItem;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum EffectiveType {

    SIGN_DATE_TO_MONTHS("Từ ngày ký/Sign Date - Tháng/Months"),
    SPECIFIC("Ngày xác định/Specific"),
    SIGN_DATE_TO_NOT_SPECIFIC("Từ ngày ký/Sign Date - Không xác định/Not Specific");
    private final String description;

    public static List<SelectItem> toSelectItemList() {
        return Arrays.stream(values())
                .map(item -> new SelectItem(item.name(), item.getDescription()))
                .collect(Collectors.toList());}
}
