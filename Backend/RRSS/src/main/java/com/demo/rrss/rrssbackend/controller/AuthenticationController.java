package com.demo.rrss.rrssbackend.controller;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.rrss.rrssbackend.rest.request.AuthenticationRequest;
import com.demo.rrss.rrssbackend.service.AuthenticationService;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

	@Autowired
	private AuthenticationService usersService;

	@PostMapping("/register")
	public ResponseEntity<Void> registerUser(@RequestBody AuthenticationRequest request) {
		usersService.addUser(request);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public ResponseEntity<Map<String, String>> loginUser(@RequestBody AuthenticationRequest request) {
		return new ResponseEntity<>(usersService.loginUser(request), HttpStatus.OK);
	}
}
