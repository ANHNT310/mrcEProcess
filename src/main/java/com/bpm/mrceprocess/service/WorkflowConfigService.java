package com.bpm.mrceprocess.service;

import com.bpm.mrceprocess.common.dtos.WorkflowConfigDTO;
import com.bpm.mrceprocess.common.dtos.WorkflowConfigStatusDTO;
import com.bpm.mrceprocess.persistence.entity.WorkflowConfig;

import java.util.List;

public interface WorkflowConfigService extends CrudService<WorkflowConfig, WorkflowConfigDTO, String> {}
