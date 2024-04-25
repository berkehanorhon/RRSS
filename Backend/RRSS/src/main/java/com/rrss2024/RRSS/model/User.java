package com.rrss2024.RRSS.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")

public class User implements UserDetails{

    // TODO CHECK CLASS IMPLEMENTATION
    // This is a no args constructor and required by JPA specification
    public User() {
        super();
    }

    // Constructor for creating a new user
    public User(String email, String password, String firstName, String lastName) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer user_id;

    @Column(unique = true, length = 100, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String username = firstName + lastName;

    @Column(nullable = false)
    private Integer token_exp = 86400;

    @Column(nullable = false)
    private Integer reputation = 0;

    @Column(nullable = false)
    private boolean is_admin = false;

    @Column(nullable = false)
    private boolean is_merchant = false;

    @Column(nullable = false)
    private boolean is_moderator = false;

    @Column(nullable = true)
    private LocalDate birthdate = LocalDate.now();

    @CreationTimestamp
    @Column(updatable = false, name = "registration_date")
    private LocalDate registrationDate;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    // Buradaki logicler sonrasında güncellenebilir.
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    
}