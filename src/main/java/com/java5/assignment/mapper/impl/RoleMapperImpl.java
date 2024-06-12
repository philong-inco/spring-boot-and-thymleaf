package com.java5.assignment.mapper.impl;

import com.java5.assignment.dto.request.RoleCreate;
import com.java5.assignment.dto.request.RoleUpdate;
import com.java5.assignment.dto.response.ColorResponse;
import com.java5.assignment.dto.response.RoleResponse;
import com.java5.assignment.entity.Color;
import com.java5.assignment.entity.Role;
import com.java5.assignment.mapper.RoleMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoleMapperImpl implements RoleMapper {

    @Override
    public Role createToEntity(RoleCreate create) {
        Role role = Role.builder()
                .name(create.getName().trim())
                .status(create.getStatus())
                .build();
        return role;
    }

    @Override
    public Role updateToEntity(RoleUpdate update, Role role) {
        role.setName(update.getName().trim());
        role.setStatus(update.getStatus());
        return role;
    }

    @Override
    public RoleResponse entityToResponse(Role role) {
        RoleResponse response = RoleResponse.builder()
                .id(role.getId())
                .name(role.getName())
                .status(role.getStatus())
                .createAt(String.valueOf(role.getCreateAt()))
                .createBy(role.getCreateBy())
                .modifyAt(String.valueOf(role.getModifyAt()))
                .modifyBy(role.getModifyBy())
                .build();
        response.convertTime();
        return response;
    }

    @Override
    public Page<RoleResponse> listEntityToListResponse(Page<Role> rolePage) {
        List<RoleResponse> responses = rolePage.getContent()
                .stream()
                .map(this::entityToResponse)
                .collect(Collectors.toList());
        return new PageImpl<>(responses, rolePage.getPageable(), rolePage.getTotalElements());

    }

    @Override
    public List<RoleResponse> listEntityToListResponse(List<Role> roleList) {
        return roleList.stream().map(this::entityToResponse).collect(Collectors.toList());
    }
}
