package com.java5.assignment.entity.base;

import com.java5.assignment.util.AutoSetTime;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@MappedSuperclass
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@EntityListeners(AutoSetTime.class)
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name = "id", updatable = false, insertable = false)
    Long id;
    @Column(name = "create_at", updatable = false)
    Long createAt;
    @Column(name = "modify_at")
    Long modifyAt;
    @Column(name = "create_by")
    String createBy;
    @Column(name = "modify_by")
    String modifyBy;
}
