package com.java5.assignment.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
public class CategoryCreate {
    @NotEmpty(message = "Name cannot is blank")
    String name;

    @NotNull(message = "Status cannot is blank")
    @Min(value = 0, message = "Invalid status")
    @Max(value = 1, message = "Invalid status")
    Integer status;
}
