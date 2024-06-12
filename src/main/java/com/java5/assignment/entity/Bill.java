package com.java5.assignment.entity;


import com.java5.assignment.common.Constants;
import com.java5.assignment.entity.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "bill")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Bill extends BaseEntity {
    @Column(name = "code", unique = true, nullable = false, length = Constants.LENGTH_CODE)
    String code;
    @Column(name = "money_total")
    BigDecimal moneyTotal;
    @Column(name = "product_total")
    Integer productTotal;
    @Column(name = "status", nullable = false)
    Integer status;

    @Column(name = "note")
    String note;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    Customer customer;
    @ManyToOne
    @JoinColumn(name = "staff_id")
    Staff staff;
    @OneToMany(mappedBy = "bill")
    Set<BillDetail> billDetailSet;
    @OneToMany(mappedBy = "bill")
    Set<BillHistory> billHistorySet;
}
