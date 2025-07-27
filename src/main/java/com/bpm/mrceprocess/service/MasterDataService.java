package com.bpm.mrceprocess.service;

import com.bpm.dtos.SelectItem;

import java.util.List;

public interface MasterDataService {

    List<SelectItem> processScope();

    List<SelectItem> SelectCategory();

    List<SelectItem> effectiveType();

    List<SelectItem> documentStatus();

    List<SelectItem> documentType();

    List<SelectItem> authorities();

    List<SelectItem> processes();
}
