package com.java5.assignment.service.impl;

import com.java5.assignment.common.GenerateCode;
import com.java5.assignment.entity.Color;
import com.java5.assignment.entity.Size;
import com.java5.assignment.mapper.ColorMapper;
import com.java5.assignment.mapper.SizeMapper;
import com.java5.assignment.repository.ColorRepository;
import com.java5.assignment.repository.SizeRepository;
import com.java5.assignment.dto.request.SizeCreate;
import com.java5.assignment.dto.request.SizeUpdate;
import com.java5.assignment.dto.response.SizeResponse;
import com.java5.assignment.service.SizeService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Primary
public class SizeServiceImpl implements SizeService {
    private SizeRepository repository;

    private GenerateCode generateCode;
    private SizeMapper mapper;

    public SizeServiceImpl(SizeRepository repository, GenerateCode generateCode, @Qualifier("sizeMapperImpl") SizeMapper mapper) {
        this.repository = repository;
        this.generateCode = generateCode;
        this.mapper = mapper;
    }

    @Override
    public Size findByName(String name) {
        return repository.findByName(name.trim()).orElse(null);
    }

    @Override
    public SizeResponse add(SizeCreate sizeCreate) {
        Size size = mapper.createToEntity(sizeCreate);
        size.setCode(generateCode.generateCode(GenerateCode.SIZE_PREFIX));
        return mapper.entityToResponse(repository.save(size));
    }

    @Override
    public SizeResponse update(SizeUpdate sizeUpdate) {
        Size size = repository.findById(sizeUpdate.getId()).orElse(null);
        if (size != null) {
            size = mapper.updateToEntity(sizeUpdate, size);
            return mapper.entityToResponse(repository.save(size));
        }
        return null;
    }

    @Override
    public Page<SizeResponse> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return mapper.listEntityToListResponse(repository.getAll(pageable));
    }

    @Override
    public Page<SizeResponse> findByStatus(int page, int size, Integer status) {
        Pageable pageable = PageRequest.of(page, size);
        return mapper.listEntityToListResponse(repository.findByStatus(pageable, status));
    }

    @Override
    public List<SizeResponse> findByStatusList(Integer status) {
        return mapper.listEntityToListResponse(repository.findByStatusList(status));
    }

    @Override
    public SizeResponse findById(Long id) {
        Size size = repository.findById(id).orElse(null);
        if (size != null)
            return mapper.entityToResponse(size);
        return null;
    }

    @Override
    public boolean existByMa(String ma) {
        return repository.existsByCode(ma.trim());
    }

    @Override
    public void delete(Long id) {
        Size size = repository.findById(id).orElse(null);
        if (size != null)
            repository.delete(size);
    }

    @Override
    public boolean existByName(String name) {
        return repository.existsByName(name.trim());
    }

    @Override
    public boolean existByNameAndDifferentId(String name, Long id) {
        if (repository.existsByNameAndDifferentId(name.trim(), id).size() > 0)
            return true;
        return false;
    }
}
