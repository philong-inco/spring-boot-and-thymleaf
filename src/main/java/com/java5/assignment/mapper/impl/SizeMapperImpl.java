package com.java5.assignment.mapper.impl;

import com.java5.assignment.dto.request.SizeCreate;
import com.java5.assignment.dto.request.SizeUpdate;
import com.java5.assignment.dto.response.ColorResponse;
import com.java5.assignment.dto.response.SizeResponse;
import com.java5.assignment.entity.Color;
import com.java5.assignment.entity.Size;
import com.java5.assignment.mapper.SizeMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SizeMapperImpl implements SizeMapper {
    @Override
    public Size createToEntity(SizeCreate create) {
        Size size = Size.builder()
                .name(create.getName().trim())
                .status(create.getStatus())
                .build();
        return size;
    }

    @Override
    public Size updateToEntity(SizeUpdate update, Size size) {
        size.setName(update.getName().trim());
        size.setStatus(update.getStatus());
        return size;
    }

    @Override
    public SizeResponse entityToResponse(Size size) {
        SizeResponse response = SizeResponse.builder()
                .id(size.getId())
                .code(size.getCode())
                .name(size.getName())
                .status(size.getStatus())
                .createAt(String.valueOf(size.getCreateAt()))
                .createBy(size.getCreateBy())
                .modifyAt(String.valueOf(size.getModifyAt()))
                .modifyBy(size.getModifyBy())
                .build();
        response.convertTime();
        return response;
    }

    @Override
    public Page<SizeResponse> listEntityToListResponse(Page<Size> sizePage) {
        List<SizeResponse> responses = sizePage.getContent()
                .stream()
                .map(this::entityToResponse)
                .collect(Collectors.toList());
        return new PageImpl<>(responses, sizePage.getPageable(), sizePage.getTotalElements());
    }

    @Override
    public List<SizeResponse> listEntityToListResponse(List<Size> sizePage) {
        return sizePage.stream().map(this::entityToResponse).collect(Collectors.toList());
    }
}
