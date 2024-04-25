package com.rrss2024.RRSS.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
@Getter

// TODO Need to reconsider the getter/setter and implement validations.
public class LoginUserDTO {
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    private String email;
    @NotBlank(message = "Password cannot be blank")
    @Min(value = 10, message = "Password should be at least 10 characters.")
    private String password;
}
