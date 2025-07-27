package com.bpm.mrceprocess.service;

import com.bpm.dtos.LazyLoadEventDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CrudService<TEntity, DTO, ID> {

    List<DTO> findAll();

    Page<DTO> paged (LazyLoadEventDTO lazyLoadEventDTO);

    DTO findById(ID id);

    DTO save(DTO dto);

    DTO update(ID id, DTO dto);

    void deleteById(ID id);
}
