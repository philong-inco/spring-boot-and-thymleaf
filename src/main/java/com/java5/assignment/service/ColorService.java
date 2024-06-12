package com.java5.assignment.service;

import com.java5.assignment.dto.request.CategoryCreate;
import com.java5.assignment.dto.request.ColorCreate;
import com.java5.assignment.dto.request.ColorUpdate;
import com.java5.assignment.dto.response.CategoryResponse;
import com.java5.assignment.dto.response.ColorResponse;
import com.java5.assignment.entity.Category;
import com.java5.assignment.entity.Color;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ColorService {

    Color findByName(String name);

    ColorResponse add(ColorCreate colorCreate);

    ColorResponse update(ColorUpdate colorUpdate);


    Page<ColorResponse> getAll(int page, int size);

    Page<ColorResponse> findByStatus(int page, int size, Integer status);

    ColorResponse findById(Long id);

    boolean existByMa(String ma);

    void delete(Long id);

    boolean existByName(String name);

    boolean existByNameAndDifferentId(String name, Long id);

    List<ColorResponse> findByStatusList(Integer status);
}
