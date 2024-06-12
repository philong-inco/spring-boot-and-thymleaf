package com.java5.assignment.service.impl;

import com.java5.assignment.common.GenerateCode;
import com.java5.assignment.dto.request.RoleCreate;
import com.java5.assignment.dto.request.RoleUpdate;
import com.java5.assignment.dto.response.RoleResponse;
import com.java5.assignment.entity.Role;
import com.java5.assignment.entity.Size;
import com.java5.assignment.mapper.RoleMapper;
import com.java5.assignment.repository.RoleRepository;
import com.java5.assignment.service.RoleService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Primary
public class RoleServiceImpl implements RoleService {

    private RoleMapper mapper;
    private RoleRepository repository;

    public RoleServiceImpl(@Qualifier("roleMapperImpl") RoleMapper mapper, RoleRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public Role findByName(String name) {
        return repository.findByName(name.trim()).orElse(null);
    }

    @Override
    public RoleResponse add(RoleCreate roleCreate) {
        Role role = mapper.createToEntity(roleCreate);
        return mapper.entityToResponse(repository.save(role));
    }

    @Override
    public RoleResponse update(RoleUpdate roleUpdate) {
        Role role = repository.findById(roleUpdate.getId()).orElse(null);
        if (role != null) {
            role = mapper.updateToEntity(roleUpdate, role);
            return mapper.entityToResponse(repository.save(role));
        }
        return null;
    }

    @Override
    public List<RoleResponse> findByStatusList(Integer status) {
        return mapper.listEntityToListResponse(repository.findByStatusList(status));
    }

    @Override
    public Page<RoleResponse> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return mapper.listEntityToListResponse(repository.getAll(pageable));
    }

    @Override
    public Page<RoleResponse> findByStatus(int page, int size, Integer status) {
        Pageable pageable = PageRequest.of(page, size);
        return mapper.listEntityToListResponse(repository.findByStatus(pageable, status));
    }

    @Override
    public RoleResponse findById(Long id) {
        Role role = repository.findById(id).orElse(null);
        if (role != null)
            return mapper.entityToResponse(role);
        return null;
    }


    @Override
    public void delete(Long id) {
        Role role = repository.findById(id).orElse(null);
        if (role != null)
            repository.delete(role);
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
