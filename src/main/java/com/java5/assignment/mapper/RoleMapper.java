package com.java5.assignment.mapper;

import com.java5.assignment.dto.request.RoleCreate;
import com.java5.assignment.dto.request.RoleUpdate;
import com.java5.assignment.dto.request.SizeCreate;
import com.java5.assignment.dto.request.SizeUpdate;
import com.java5.assignment.dto.response.RoleResponse;
import com.java5.assignment.dto.response.SizeResponse;
import com.java5.assignment.entity.Role;
import com.java5.assignment.entity.Size;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface RoleMapper {
    Role createToEntity(RoleCreate create);

    Role updateToEntity(RoleUpdate update, Role role);

    RoleResponse entityToResponse(Role role);

    Page<RoleResponse> listEntityToListResponse(Page<Role> rolePage);

    List<RoleResponse> listEntityToListResponse(List<Role> roleList);
}
