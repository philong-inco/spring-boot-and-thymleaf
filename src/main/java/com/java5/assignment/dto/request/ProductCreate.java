package com.java5.assignment.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductCreate {
    @NotEmpty(message = "Name cannot blank")
    String name;
    @NotEmpty(message = "Description cannot blank")
    String description;
    @NotNull(message = "Status cannot null")
    Integer status;
    @NotNull(message = "Category cannot null")
    Long idCategory;
}
