package com.java5.assignment.service;

import com.java5.assignment.dto.request.RoleCreate;
import com.java5.assignment.dto.request.RoleUpdate;
import com.java5.assignment.dto.request.SizeCreate;
import com.java5.assignment.dto.request.SizeUpdate;
import com.java5.assignment.dto.response.RoleResponse;
import com.java5.assignment.dto.response.SizeResponse;
import com.java5.assignment.entity.Role;
import com.java5.assignment.entity.Size;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleService {
    Role findByName(String name);

    RoleResponse add(RoleCreate roleCreate);

    RoleResponse update(RoleUpdate roleUpdate);

    List<RoleResponse> findByStatusList(Integer status);
    Page<RoleResponse> getAll(int page, int size);

    Page<RoleResponse> findByStatus(int page, int size, Integer status);

    RoleResponse findById(Long id);


    void delete(Long id);

    boolean existByName(String name);

    boolean existByNameAndDifferentId(String name, Long id);


}
