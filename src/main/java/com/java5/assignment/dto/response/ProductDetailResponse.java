package com.java5.assignment.dto.response;

import com.java5.assignment.common.ConvertTime;
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
public class ProductDetailResponse {
    Long id;
    BigDecimal price;
    Integer amount;
    String image;
    Integer status;
    String productName;
    String sizeName;
    String colorName;
    String createAt;
    String modifyAt;
    String createBy;
    String modifyBy;

    public void convertTime() {
        this.createAt = ConvertTime.convert(this.createAt);
        this.modifyAt = ConvertTime.convert(this.modifyAt);
    }
}
