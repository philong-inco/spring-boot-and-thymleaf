package com.java5.assignment.entity;

import com.java5.assignment.common.Constants;
import com.java5.assignment.entity.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Entity
@Table(name = "customer")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
//@ToString
public class Customer extends BaseEntity {
    @Column(name = "code", unique = true, length = Constants.LENGTH_CODE)
    String code;
    @Column(name = "name", length = Constants.LENGTH_NAME_MAX)
    String name;
    @Column(name = "phone", unique = true, length = 12)
    String phone;
    @Column(name = "email", unique = true, length = Constants.LENGTH_EMAIL_MAX)
    String email;
    @Column(name = "password")
    String password;
    @Column(name = "address", length = Constants.LENGTH_DESCRIPTION)
    String address;
    @Column(name = "status", nullable = false)
    Integer status;

    @Column(name = "session_id")
    String sessionId;
    @ManyToOne
    @JoinColumn(name = "role_id")
    Role role;
    @OneToMany(mappedBy = "customer")
    Set<Cart> cartSet;
    @OneToMany(mappedBy = "customer")
    Set<FavoriteList> favoriteListSet;
    @OneToMany(mappedBy = "customer")
    Set<Bill> billSet;
}
