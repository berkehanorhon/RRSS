package com.demo.rrss.rrssbackend.rest.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class UsersRequest {

	private String username;

	private String password;

	private String firstName;

	private String lastName;

	private String birthDate;

	private String email;

}