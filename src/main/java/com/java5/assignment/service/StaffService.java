package com.java5.assignment.service;

import com.java5.assignment.dto.request.StaffCreate;
import com.java5.assignment.dto.request.StaffUpdate;
import com.java5.assignment.dto.response.StaffResponse;
import com.java5.assignment.entity.Staff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface StaffService {
    StaffResponse add(StaffCreate staffCreate);

    StaffResponse update(StaffUpdate staffUpdate);

    void delete(Long id);

    Page<StaffResponse> getAll(Pageable pageable);

    Page<StaffResponse> findByStatus(Pageable pageable, Integer status);

    boolean isExistByUsername(String username);

    StaffResponse findById(Long id);

    boolean existByEmail(String email);

    boolean existByEmailAndDifferentId(String email, Long id);


}
