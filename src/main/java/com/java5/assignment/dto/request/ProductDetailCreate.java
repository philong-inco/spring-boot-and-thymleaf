package com.java5.assignment.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDetailCreate {
    @NotBlank(message = "Price cannot blank")
    String price;
    @NotBlank(message = "Amount cannot blank")
    String amount;
    String image;
    @NotNull(message = "status cannot blank")
    Integer status;

    Long idProduct;
    @NotNull(message = "Size cannot blank")
    Long idSize;
    @NotNull(message = "Color cannot blank")
    Long idColor;
}
