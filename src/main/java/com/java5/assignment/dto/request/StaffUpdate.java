package com.java5.assignment.dto.request;

import com.java5.assignment.common.Constants;
import com.java5.assignment.entity.Role;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class StaffUpdate {
    Long id;

    @NotBlank(message = "name cannot bank")
    String name;
    @NotBlank(message = "email cannot bank")
    String email;
    @NotNull(message = "Status cannot is blank")
    @Min(value = 0, message = "Status is invalid")
    @Max(value = 1, message = "Status is invalid")
    Integer status;
    @NotNull(message = "Status cannot is blank")
    Long idRole;
}
