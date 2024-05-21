package com.demo.rrss.rrssbackend.controller;

import com.demo.rrss.rrssbackend.rest.request.UsersProfileRequest;
import com.demo.rrss.rrssbackend.rest.request.UsersRequest;
import com.demo.rrss.rrssbackend.service.UsersService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.demo.rrss.rrssbackend.service.JwtUtil;

@RestController
public class UsersController {

    @Autowired
    UsersService service;

    @Autowired
    JwtUtil jwtUtil;

    @ModelAttribute // TODO Herhangi bir hata durumunda 403 döndürülecek
	public void addUserIdToModel(@RequestHeader(value="Authorization") String bearerToken, Model model) {
		String token = bearerToken.substring(7);
		Long userId = jwtUtil.extractUserId(token);
		model.addAttribute("userId", userId);
	}

    @GetMapping("/get-user")
    @ResponseStatus(HttpStatus.OK)
    public UsersRequest getUser(@RequestParam Long userId) {
        return service.getUser(userId);
    }

    @GetMapping("/get-user-profile")
    @ResponseStatus(HttpStatus.OK)
    public UsersProfileRequest getUserProfile(@RequestParam Long userId) {
        return service.getUserProfile(userId);
    }

    @PatchMapping(value = "/update-user")
    @ResponseStatus(HttpStatus.OK)
    public void updateUser(@RequestBody UsersRequest user, Model model) {
        service.updateUser(user.getUsername(), user, model);
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