package com.bpm.mrceprocess.service;

import com.bpm.dtos.SelectItem;
import com.bpm.mrceprocess.common.enums.WorkflowConfigScope;

import java.util.List;

public interface MasterDataService {

    List<SelectItem> processScope();

    List<SelectItem> SelectCategory();

    List<SelectItem> effectiveType();

    List<SelectItem> documentStatus();

    List<SelectItem> documentType();

    List<SelectItem> authorities();

    List<SelectItem> processes();

    List<SelectItem> workflow();

    List<SelectItem> processWorkflow(WorkflowConfigScope scope);
}
