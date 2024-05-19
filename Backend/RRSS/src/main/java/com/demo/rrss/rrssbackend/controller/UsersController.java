package com.demo.rrss.rrssbackend.controller;

import com.demo.rrss.rrssbackend.rest.request.UsersProfileRequest;
import com.demo.rrss.rrssbackend.rest.request.UsersRequest;
import com.demo.rrss.rrssbackend.service.ProductService;
import com.demo.rrss.rrssbackend.service.UsersService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
public class UsersController {

    @Autowired
    UsersService service;

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
    public void updateUser(@RequestBody UsersRequest user) {
        service.updateUser(user.getUsername(), user);
    }

    @DeleteMapping(value = "/delete-user")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@RequestParam Long userId) {
        service.deleteUser(userId);
    }
}