package com.rrss2024.RRSS.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rrss2024.RRSS.dto.LoginUserDTO;
import com.rrss2024.RRSS.dto.RegisterUserDTO;
import com.rrss2024.RRSS.model.User;
import com.rrss2024.RRSS.service.AuthenticationService;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody RegisterUserDTO registerUserDto) {
        User registeredUser = authenticationService.signUp(registerUserDto);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<User> authenticate(@RequestBody LoginUserDTO loginUserDto) {
        User loggedInUser = authenticationService.authenticate(loginUserDto);
        return ResponseEntity.ok(loggedInUser);
    }
}
