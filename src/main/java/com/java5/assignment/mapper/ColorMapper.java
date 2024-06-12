package com.java5.assignment.mapper;

import com.java5.assignment.dto.request.CategoryCreate;
import com.java5.assignment.dto.request.CategoryUpdate;
import com.java5.assignment.dto.request.ColorCreate;
import com.java5.assignment.dto.request.ColorUpdate;
import com.java5.assignment.dto.response.CategoryResponse;
import com.java5.assignment.dto.response.ColorResponse;
import com.java5.assignment.entity.Category;
import com.java5.assignment.entity.Color;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ColorMapper {
    Color createToEntity(ColorCreate create);

    Color updateToEntity(ColorUpdate update, Color color);

    ColorResponse entityToResponse(Color color);

    Page<ColorResponse> listEntityToListResponse(Page<Color> colorPages);

    List<ColorResponse> listEntityToListResponse(List<Color> colorPages);
}
