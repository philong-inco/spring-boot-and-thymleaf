package com.java5.assignment.mapper;

import com.java5.assignment.dto.request.CartCreate;
import com.java5.assignment.dto.request.CartUpdate;
import com.java5.assignment.dto.request.CategoryCreate;
import com.java5.assignment.dto.request.CategoryUpdate;
import com.java5.assignment.dto.response.CartResponse;
import com.java5.assignment.dto.response.CategoryResponse;
import com.java5.assignment.entity.Cart;
import com.java5.assignment.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CartMapper {
    Cart createToEntity(CartCreate create);

    Cart updateToEntity(CartUpdate update, Cart cart);


}
