package com.java5.assignment.dto.request;

import com.java5.assignment.entity.Customer;
import com.java5.assignment.entity.ProductDetail;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartCreate {

    Integer amount;

    Long customerId;

    Long productDetailId;
}
