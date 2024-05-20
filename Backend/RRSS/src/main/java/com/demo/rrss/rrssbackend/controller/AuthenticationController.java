package com.demo.rrss.rrssbackend.controller;

import java.util.HashMap;
import java.util.Map;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.demo.rrss.rrssbackend.entity.Users;
import com.demo.rrss.rrssbackend.rest.request.AuthenticationRequest;
import com.demo.rrss.rrssbackend.service.JwtUtil;
import com.demo.rrss.rrssbackend.service.AuthenticationService;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private JwtUtil jwtUtil;

	@Autowired
	private AuthenticationService usersService;

	@PostMapping("/register")
	public ResponseEntity<Void> registerUser(@RequestBody AuthenticationRequest request) {
		usersService.addUser(request);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public ResponseEntity<Map<String, String>> loginUser(@RequestBody AuthenticationRequest request) {
		Users existingUser = usersService.getUserByUsername(request.getUsername());

		if (existingUser != null && BCrypt.checkpw(request.getPassword(), existingUser.getPassword())) {
			Map<String, String> response = new HashMap<>();
			response.put("token", jwtUtil.generateToken(existingUser));
			response.put("userId", existingUser.getUserId().toString());
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect username or password");
		}
	}
}
