package com.rrss2024.RRSS.dto;

import lombok.Getter;
@Getter

// TODO Need to reconsider the getter/setter and implement validations.
public class RegisterUserDTO {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
}
