package com.java5.assignment.mapper.impl;

import com.java5.assignment.dto.request.StaffCreate;
import com.java5.assignment.dto.request.StaffUpdate;
import com.java5.assignment.dto.response.StaffResponse;
import com.java5.assignment.entity.Size;
import com.java5.assignment.entity.Staff;
import com.java5.assignment.mapper.StaffMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StaffMapperImpl implements StaffMapper {
    @Override
    public Staff createToEntity(StaffCreate create) {
        Staff staff = Staff.builder()
                .username(create.getUsername())
                .name(create.getName().trim())
                .email(create.getEmail().trim())
                .password(create.getPassword())
                .status(create.getStatus())
                .build();
        return staff;
    }

    @Override
    public Staff updateToEntity(StaffUpdate update, Staff staff) {
        staff.setName(update.getName());
        staff.setStatus(update.getStatus());
        staff.setEmail(update.getEmail());
        return staff;
    }

    @Override
    public StaffResponse entityToResponse(Staff staff) {
        StaffResponse response = StaffResponse.builder()
                .name(staff.getName())
                .username(staff.getUsername())
                .email(staff.getEmail())
                .id(staff.getId())
                .status(staff.getStatus())
                .createAt(String.valueOf(staff.getCreateAt()))
                .createBy(staff.getCreateBy())
                .modifyAt(String.valueOf(staff.getModifyAt()))
                .modifyBy(staff.getModifyBy())
                .role(staff.getRole().getName())
                .build();
        response.convertTime();
        return response;
    }

    @Override
    public Page<StaffResponse> listEntityToListResponse(Page<Staff> staffPage) {
        List<StaffResponse> responses = staffPage.getContent()
                .stream().map(this::entityToResponse).collect(Collectors.toList());
        return new PageImpl<>(responses, staffPage.getPageable(), staffPage.getTotalElements());
    }
}
