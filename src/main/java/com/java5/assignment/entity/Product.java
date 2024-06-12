package com.java5.assignment.entity;


import com.java5.assignment.common.Constants;
import com.java5.assignment.entity.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Entity
@Table(name = "product")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Product extends BaseEntity {
    @Column(name = "code", nullable = false, unique = true, length = Constants.LENGTH_CODE)
    String code;
    @Column(name = "name", nullable = false, unique = true, length = Constants.LENGTH_NAME_MAX)
    String name;
    @Column(name = "description", length = Constants.LENGTH_DESCRIPTION)
    String description;
    @Column(name = "status", nullable = false)
    Integer status;
    @ManyToOne
    @JoinColumn(name = "category_id")
    Category category;
    @OneToMany(mappedBy = "product")
    Set<ProductDetail> productDetailSet;
    @OneToMany(mappedBy = "product")
    Set<FavoriteList> favoriteListSet;

}
