package com.java5.assignment.entity;


import com.java5.assignment.common.Constants;
import com.java5.assignment.entity.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Entity
@Table(name = "category")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Category extends BaseEntity {
    @Column(name = "code", unique = true, nullable = false, length = Constants.LENGTH_CODE)
    String code;
    @Column(name = "name", unique = true, nullable = false, length = Constants.LENGTH_NAME_MAX)
    String name;
    @Column(name = "status", nullable = false)
    Integer status;
    @OneToMany(mappedBy = "category")
    Set<Product> productSet;

}
