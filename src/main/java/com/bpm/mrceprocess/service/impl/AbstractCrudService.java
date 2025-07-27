package com.bpm.mrceprocess.service.impl;

import com.bpm.dtos.LazyLoadEventDTO;
import com.bpm.enums.ApplicationMessage;
import com.bpm.exception.ApplicationException;
import com.bpm.mrceprocess.service.CrudService;
import com.bpm.utils.FilterSpecification;
import com.bpm.utils.PageableHelper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public abstract class AbstractCrudService<T, D, ID>  implements CrudService<T, D, ID> {

    protected abstract JpaRepository<T, ID> getRepository();
    protected abstract T toEntity(D dto);
    protected abstract D toDTO(T entity);
    protected abstract T toUpdateEntity(T entity, D dto);

    @Override
    public List<D> findAll() {
        return getRepository().findAll().stream().map(this::toDTO).toList();
    }

    @Override
    public Page<D> paged (LazyLoadEventDTO lazyLoadEventDTO) {
        FilterSpecification<T> spec = new FilterSpecification<>(lazyLoadEventDTO);
        Pageable pageable = PageableHelper.createPageable(lazyLoadEventDTO);
        JpaRepository<T, ID> repository = getRepository();
        if (!(repository instanceof JpaSpecificationExecutor))
            throw new ApplicationException(ApplicationMessage.INTERNAL_SERVER_ERROR);
        return ((JpaSpecificationExecutor<T>) repository).findAll(spec, pageable).map(this::toDTO);
    }

    @Override
    public D findById(ID id) {
        return getRepository().findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new ApplicationException(ApplicationMessage.NOT_FOUND));
    }

    @Override
    public D save(D dto) {
        T entity = toEntity(dto);
        return toDTO(getRepository().save(entity));
    }

    @Override
    public void deleteById(ID id) {
        getRepository().deleteById(id);
    }

    @Override
    public D update(ID id, D dto) {
        T entity = getRepository().findById(id)
                .orElseThrow(() -> new ApplicationException(ApplicationMessage.NOT_FOUND));
        return toDTO(getRepository().save(toUpdateEntity(entity, dto)));
    }
}