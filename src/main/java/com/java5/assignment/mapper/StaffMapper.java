package com.java5.assignment.mapper;

import com.java5.assignment.dto.request.SizeCreate;
import com.java5.assignment.dto.request.SizeUpdate;
import com.java5.assignment.dto.request.StaffCreate;
import com.java5.assignment.dto.request.StaffUpdate;
import com.java5.assignment.dto.response.SizeResponse;
import com.java5.assignment.dto.response.StaffResponse;
import com.java5.assignment.entity.Size;
import com.java5.assignment.entity.Staff;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public interface StaffMapper {
    Staff createToEntity(StaffCreate create);

    Staff updateToEntity(StaffUpdate update, Staff staff);

    StaffResponse entityToResponse(Staff staff);

    Page<StaffResponse> listEntityToListResponse(Page<Staff> staffPage);
}
