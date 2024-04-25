package com.rrss2024.RRSS.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rrss2024.RRSS.model.User;
import com.rrss2024.RRSS.repository.UserRepository;
import com.rrss2024.RRSS.dto.RegisterUserDTO;
import com.rrss2024.RRSS.dto.LoginUserDTO;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public User signUp(RegisterUserDTO registerUserDTO) {
        userRepository.findByEmail(registerUserDTO.getEmail())
        .ifPresent(user -> {
            throw new IllegalArgumentException("User already exists: " + user.getEmail());
        });

        User user = new User(registerUserDTO.getEmail(), 
                             passwordEncoder.encode(registerUserDTO.getPassword()), 
                             registerUserDTO.getFirstName(),
                             registerUserDTO.getLastName());
        return userRepository.save(user);
    }

    public User authenticate(LoginUserDTO loginUserDTO) throws AuthenticationException{
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginUserDTO.getEmail(), loginUserDTO.getPassword())
        );

        if (authentication.isAuthenticated()) {
            return userRepository.findByEmail(loginUserDTO.getEmail())
            .orElseThrow( () -> new IllegalStateException("User not found after authentication."));
        } else {
            throw new IllegalStateException("Authentication failed.");
        }
    }
}
