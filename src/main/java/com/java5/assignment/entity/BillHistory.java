package com.java5.assignment.entity;


import com.java5.assignment.common.Constants;
import com.java5.assignment.entity.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "bill_history")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BillHistory extends BaseEntity {
    @Column(name = "note", nullable = false, length = Constants.LENGTH_DESCRIPTION)
    String note;
    @ManyToOne
    @JoinColumn(name = "bill_id")
    Bill bill;
    @ManyToOne
    @JoinColumn(name = "staff_id")
    Staff staff;
}
