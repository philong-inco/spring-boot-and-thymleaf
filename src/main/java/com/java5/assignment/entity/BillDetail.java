package com.java5.assignment.entity;


import com.java5.assignment.entity.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Entity
@Table(name = "bill_detail")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BillDetail extends BaseEntity {
    @Column(name = "amount")
    Integer amount;

    @Column(name = "price")
    BigDecimal price;

    @Column(name = "status")
    Integer status;

    @ManyToOne
    @JoinColumn(name = "bill_id")
    Bill bill;
    @ManyToOne
    @JoinColumn(name = "product_detail_id")
    ProductDetail productDetail;
}
