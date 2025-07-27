package com.bpm.mrceprocess.service.impl;

import com.bpm.dtos.SelectItem;
import com.bpm.mrceprocess.common.enums.DocumentStatus;
import com.bpm.mrceprocess.common.enums.EffectiveType;
import com.bpm.mrceprocess.common.enums.ProcessScopeEnum;
import com.bpm.mrceprocess.persistence.repository.AuthorityRepository;
import com.bpm.mrceprocess.persistence.repository.CategoryRepository;
import com.bpm.mrceprocess.persistence.repository.DocumentTypeRepository;
import com.bpm.mrceprocess.persistence.repository.GeneralInformationRepository;
import com.bpm.mrceprocess.service.MasterDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MasterDataServiceImpl implements MasterDataService {

    private final CategoryRepository categoryRepository;
    private final DocumentTypeRepository documentTypeRepository;
    private final AuthorityRepository authorityRepository;
    private final GeneralInformationRepository generalInformationRepository;

    @Override
    public List<SelectItem> processScope() {
        return ProcessScopeEnum.toSelectItemList();
    }

    @Override
    public List<SelectItem> SelectCategory() {
        return categoryRepository.findAll().stream()
                .map(m -> new SelectItem(m.getId(), m.getVieName() + " / " + m.getEnName()))
                .toList();
    }

    @Override
    public List<SelectItem> effectiveType() {
        return EffectiveType.toSelectItemList();
    }

    @Override
    public List<SelectItem> documentStatus() {
        return DocumentStatus.toSelectItemList().stream().toList();
    }

    @Override
    public List<SelectItem> documentType() {
        return documentTypeRepository.findAll().stream()
                .map(m -> new SelectItem(m.getCode(), m.getViName() + " / " + m.getEnName()))
                .toList();
    }

    @Override
    public List<SelectItem> authorities() {
        return authorityRepository.findAll().stream()
                .map(m -> new SelectItem(m.getCode(), m.getName()))
                .toList();    }

    @Override
    public List<SelectItem> processes() {
        return generalInformationRepository.findAll().stream()
                .map(m -> new SelectItem(m.getId(), m.getCode() + " - " + m.getName()))
                .toList();
    }
}
