package com.demo.rrss.rrssbackend.entity;

import java.sql.Date;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import java.sql.Timestamp;

@Setter
@Getter
@Entity
@Table(name = "Users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "birthdate", nullable = false)
    private Date birthDate;

    @CreationTimestamp
    @Column(name = "registration_date", nullable = false, updatable = false)
    private Timestamp registrationDate;

    @Column(name = "is_admin", nullable = false)
    private Boolean isAdmin;

    @Column(name = "is_moderator", nullable = false)
    private Boolean isModerator;

    @Column(name = "is_merchant", nullable = false)
    private Boolean isMerchant;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "avatar_image_path")
    private String avatarImagePath;

    @Column(name = "reputation")
    private Integer reputation;

    @Column(name = "token_exp")
    private Timestamp tokenExp;

    private Boolean isBanned;
}
