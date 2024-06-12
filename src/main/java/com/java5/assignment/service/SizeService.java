package com.java5.assignment.service;

import com.java5.assignment.dto.request.ColorCreate;
import com.java5.assignment.dto.request.ColorUpdate;
import com.java5.assignment.dto.request.SizeCreate;
import com.java5.assignment.dto.request.SizeUpdate;
import com.java5.assignment.dto.response.ColorResponse;
import com.java5.assignment.dto.response.SizeResponse;
import com.java5.assignment.entity.Color;
import com.java5.assignment.entity.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SizeService {
    Size findByName(String name);

    SizeResponse add(SizeCreate sizeCreate);

    SizeResponse update(SizeUpdate sizeUpdate);


    Page<SizeResponse> getAll(int page, int size);

    Page<SizeResponse> findByStatus(int page, int size, Integer status);

    List<SizeResponse> findByStatusList(Integer status);
    SizeResponse findById(Long id);

    boolean existByMa(String ma);

    void delete(Long id);

    boolean existByName(String name);

    boolean existByNameAndDifferentId(String name, Long id);


}
