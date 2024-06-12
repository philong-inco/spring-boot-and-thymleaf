package com.java5.assignment.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
public class CustomerUpdate {

    Long id;
    @NotEmpty(message = "Name cannot is blank")
    @Pattern(regexp = "^[a-zA-Z\\p{L}\\s]+$", message = "Invalid name")
    String name;
    @NotEmpty(message = "phone cannot is blank")
            @Size(min = 1, max = 12, message = "Invalid phone :D")
    String phone;
    @NotEmpty(message = "email cannot is blank")
    @Email(message = "Invalid email")
    String email;
    @NotEmpty(message = "address cannot is blank")
    String address;
    @NotNull(message = "Status cannot null")
    Integer status;
}
