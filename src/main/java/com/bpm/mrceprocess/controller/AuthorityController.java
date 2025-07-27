package com.bpm.mrceprocess.controller;

import com.bpm.mrceprocess.common.dtos.AuthorityDTO;
import com.bpm.mrceprocess.service.AuthorityService;
import com.bpm.mrceprocess.service.CrudService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authority")
@RequiredArgsConstructor
@Tag(name = "Authority Management", description = "APIs for managing authorities")
public class AuthorityController extends AbstractCrudController<AuthorityDTO, String> {

    private final AuthorityService authorityService;

    @Override
    protected CrudService<?, AuthorityDTO, String> getService() {
        return authorityService;
    }
}
