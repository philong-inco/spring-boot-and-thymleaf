package com.java5.assignment.mapper;

import com.java5.assignment.dto.request.ColorCreate;
import com.java5.assignment.dto.request.ColorUpdate;
import com.java5.assignment.dto.request.SizeCreate;
import com.java5.assignment.dto.request.SizeUpdate;
import com.java5.assignment.dto.response.ColorResponse;
import com.java5.assignment.dto.response.SizeResponse;
import com.java5.assignment.entity.Color;
import com.java5.assignment.entity.Size;
import com.java5.assignment.repository.SizeRepository;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SizeMapper {
    Size createToEntity(SizeCreate create);

    Size updateToEntity(SizeUpdate update, Size size);

    SizeResponse entityToResponse(Size size);

    Page<SizeResponse> listEntityToListResponse(Page<Size> sizePage);

    List<SizeResponse> listEntityToListResponse(List<Size> sizePage);
}
