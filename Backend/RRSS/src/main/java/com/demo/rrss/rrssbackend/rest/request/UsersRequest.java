package com.demo.rrss.rrssbackend.rest.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Setter
@Getter
@Data
public class UsersRequest {
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private Date birthdate;
    private Boolean isAdmin;
    private Boolean isModerator;
    private Boolean isMerchant;
    private String avatarImagePath;
    private Integer reputation;
    private Long userId;
}