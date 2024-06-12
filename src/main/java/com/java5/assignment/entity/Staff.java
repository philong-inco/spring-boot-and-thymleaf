package com.java5.assignment.entity;


import com.java5.assignment.common.Constants;
import com.java5.assignment.entity.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Entity
@Table(name = "staff")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
//@ToString
public class Staff extends BaseEntity {
    @Column(name = "username", unique = true, nullable = false, length = Constants.LENGTH_USERNAME_MAX)
    String username;
    @Column(name = "password", nullable = false)
    String password;
    @Column(name = "name", nullable = false, length = Constants.LENGTH_NAME_MAX)
    String name;
    @Column(name = "email", length = Constants.LENGTH_EMAIL_MAX)
    String email;
    @Column(name = "status", nullable = false)
    Integer status;
    @ManyToOne
    @JoinColumn(name = "role_id")
    Role role;
    @OneToMany(mappedBy = "staff")
    Set<Bill> billSet;
    @OneToMany(mappedBy = "staff")
    Set<BillHistory> billHistorySet;
}
