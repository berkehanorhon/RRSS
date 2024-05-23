package com.demo.rrss.rrssbackend.controller;

import com.demo.rrss.rrssbackend.entity.Users;
import com.demo.rrss.rrssbackend.rest.request.UsersProfileRequest;
import com.demo.rrss.rrssbackend.rest.request.UsersRequest;
import com.demo.rrss.rrssbackend.service.UsersService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.demo.rrss.rrssbackend.service.JwtUtil;

@RestController
public class UsersController {

    @Autowired
    UsersService service;

    @Autowired
    JwtUtil jwtUtil;

    @ModelAttribute
	public void addUserIdToModel(@RequestHeader(value="Authorization", required=false) String bearerToken, Model model) {
		if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Authorization header is missing or invalid");
		}
		String token = bearerToken.substring(7);
		Long userId;
		try {
			userId = jwtUtil.extractUserId(token);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid token");
		}
		if (userId == null) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User ID not found in token");
		}
		model.addAttribute("userId", userId);
	}

    @GetMapping("/get-user")
    @ResponseStatus(HttpStatus.OK)
    public UsersRequest getUser(@RequestParam Long userId) {
        return service.getUser(userId);
    }

    @GetMapping("/get-all-users")
    @ResponseStatus(HttpStatus.OK)
    public List<UsersRequest> getAllUsers(Model model) {
        return service.getAllUsers(model);
    }

    @GetMapping("/get-user-profile")
    @ResponseStatus(HttpStatus.OK)
    public UsersProfileRequest getUserProfile(@RequestParam Long userId, Model model) {
        return service.getUserProfile(userId,model);
    }

    @PatchMapping(value = "/update-user")
    @ResponseStatus(HttpStatus.OK)
    public void updateUser(@RequestBody Users user, Model model) {
        service.updateUser(user.getUserId(), user, model);
    }

    @DeleteMapping(value = "/delete-user")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@RequestParam Long userId, Model model) {
        service.deleteUser(userId, model);
    }

    @GetMapping(value = "/get-balance")
    @ResponseStatus(HttpStatus.OK)
    public String getBalance(Model model) {
        return service.getBalance(model);
    }
}