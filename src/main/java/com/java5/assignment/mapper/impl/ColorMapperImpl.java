package com.java5.assignment.mapper.impl;

import com.java5.assignment.dto.request.ColorCreate;
import com.java5.assignment.dto.request.ColorUpdate;
import com.java5.assignment.dto.response.ColorResponse;
import com.java5.assignment.entity.Color;
import com.java5.assignment.mapper.ColorMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ColorMapperImpl implements ColorMapper {
    @Override
    public Color createToEntity(ColorCreate create) {
        Color color = Color.builder()
                .name(create.getName().trim())
                .status(create.getStatus())
                .build();
        return color;
    }

    @Override
    public Color updateToEntity(ColorUpdate update, Color color) {
        color.setName(update.getName().trim());
        color.setStatus(update.getStatus());
        return color;
    }

    @Override
    public ColorResponse entityToResponse(Color color) {
        ColorResponse response = ColorResponse.builder()
                .id(color.getId())
                .code(color.getCode())
                .name(color.getName())
                .status(color.getStatus())
                .createAt(String.valueOf(color.getCreateAt()))
                .createBy(color.getCreateBy())
                .modifyAt(String.valueOf(color.getModifyAt()))
                .modifyBy(color.getModifyBy())
                .build();
        response.convertTime();
        return response;
    }

    @Override
    public Page<ColorResponse> listEntityToListResponse(Page<Color> colorPages) {
        List<ColorResponse> responses = colorPages.getContent()
                .stream()
                .map(this::entityToResponse)
                .collect(Collectors.toList());
        return new PageImpl<>(responses, colorPages.getPageable(), colorPages.getTotalElements());
    }

    @Override
    public List<ColorResponse> listEntityToListResponse(List<Color> colorPages) {
        return colorPages.stream()
                .map(this::entityToResponse)
                .collect(Collectors.toList());
    }
}
