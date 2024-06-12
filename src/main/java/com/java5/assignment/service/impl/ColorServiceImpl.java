package com.java5.assignment.service.impl;

import com.java5.assignment.common.GenerateCode;
import com.java5.assignment.dto.request.ColorCreate;
import com.java5.assignment.dto.request.ColorUpdate;
import com.java5.assignment.dto.response.ColorResponse;
import com.java5.assignment.entity.Color;
import com.java5.assignment.mapper.ColorMapper;
import com.java5.assignment.repository.ColorRepository;
import com.java5.assignment.service.ColorService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@Primary
public class ColorServiceImpl implements ColorService {
    private ColorRepository repository;

    private GenerateCode generateCode;
    private ColorMapper mapper;

    public ColorServiceImpl(ColorRepository repository,@Qualifier("colorMapperImpl") ColorMapper mapper, GenerateCode generateCode) {
        this.repository = repository;
        this.mapper = mapper;
        this.generateCode = generateCode;
    }

    @Override
    public Color findByName(String name) {
        return repository.findByName(name.trim()).orElse(null);
    }

    @Override
    public ColorResponse add(ColorCreate colorCreate) {
        Color color = mapper.createToEntity(colorCreate);
        color.setCode(generateCode.generateCode(GenerateCode.COLOR_PREFIX));
        return mapper.entityToResponse(repository.save(color));
    }

    @Override
    public ColorResponse update(ColorUpdate colorUpdate) {
        Color color = repository.findById(colorUpdate.getId()).orElse(null);
        if (color != null) {
            color = mapper.updateToEntity(colorUpdate, color);
            return mapper.entityToResponse(repository.save(color));
        }
        return null;
    }

    @Override
    public Page<ColorResponse> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return mapper.listEntityToListResponse(repository.getAll(pageable));
    }

    @Override
    public Page<ColorResponse> findByStatus(int page, int size, Integer status) {
        Pageable pageable = PageRequest.of(page, size);
        return mapper.listEntityToListResponse(repository.findByStatus(pageable, status));
    }

    @Override
    public ColorResponse findById(Long id) {
        Color color = repository.findById(id).orElse(null);
        if (color != null)
            return mapper.entityToResponse(color);
        return null;
    }

    @Override
    public boolean existByMa(String ma) {
        return repository.existsByCode(ma.trim());
    }

    @Override
    public void delete(Long id) {
        Color color = repository.findById(id).orElse(null);
        if (color != null)
            repository.delete(color);
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

    @Override
    public List<ColorResponse> findByStatusList(Integer status) {
        return mapper.listEntityToListResponse(repository.findByStatusList(status));
    }


}
