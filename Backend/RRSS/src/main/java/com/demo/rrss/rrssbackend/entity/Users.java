package com.demo.rrss.rrssbackend.entity;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Users {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long userId;

	@Column(name = "username", nullable = false)
	private String username;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "first_name", nullable = false)
	private String firstName;

	@Column(name = "last_name", nullable = false)
	private String lastName;

	@Column(name = "birthdate", nullable = false)
	private Date birthDate;

	@Column(name = "registration_date", nullable = false)
	private java.sql.Timestamp registrationDate;

	@Column(name = "is_admin", nullable = false)
	private Boolean isAdmin;

	@Column(name = "is_moderator", nullable = false)
	private Boolean isModerator;

	@Column(name = "is_merchant", nullable = false)
	private Boolean isMerchant;

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "reputation")
	private Integer reputation;

	@Column(name = "token_exp")
	private Integer tokenExp;

}
