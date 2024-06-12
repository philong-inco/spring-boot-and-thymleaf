package com.java5.assignment.entity;


import com.java5.assignment.entity.base.BaseEntity;
import com.java5.assignment.entity.Size;
import jakarta.persistence.*;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "product_detail")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ProductDetail extends BaseEntity {
    @Column(name = "price", nullable = false)
    BigDecimal price;
    @Column(name = "amount", nullable = false)
    Integer amount;
    @Column(name = "image")
    String image;
    @Column(name = "status", nullable = false)
    Integer status;
    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;
    @ManyToOne
    @JoinColumn(name = "size_id")
    Size size;
    @ManyToOne
    @JoinColumn(name = "color_id")
    Color color;
    @OneToMany(mappedBy = "productDetail")
    Set<Cart> cartSet;
    @OneToMany(mappedBy = "productDetail")
    Set<BillDetail> billDetailSet;
}
