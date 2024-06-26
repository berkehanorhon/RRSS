package com.demo.rrss.rrssbackend.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.server.ResponseStatusException;

import com.demo.rrss.rrssbackend.entity.Users;
import com.demo.rrss.rrssbackend.repository.UserBalanceRepository;
import com.demo.rrss.rrssbackend.repository.UsersRepository;
import com.demo.rrss.rrssbackend.rest.request.UsersProfileRequest;
import com.demo.rrss.rrssbackend.rest.request.UsersRequest;

@Service
public class UsersService {

    @Autowired
    UsersRepository repository;

    @Autowired
    ProductService pservice;

    @Autowired
    ReviewService rservice;

    @Autowired
    BlogPostService bpservice;

    @Autowired
    BookmarkListService blservice;

    @Autowired
    UserBalanceRepository balanceRepository;

    public UsersRequest getUser(Long userId) {
        Optional<Users> response = repository.findById(userId);
        Users user = response.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    
        UsersRequest userRequest = new UsersRequest();
        userRequest.setUsername(user.getUsername());
        userRequest.setEmail(user.getEmail());
        userRequest.setFirstName(user.getFirstName());
        userRequest.setLastName(user.getLastName());
        userRequest.setBirthdate(user.getBirthDate());
        userRequest.setIsAdmin(user.getIsAdmin());
        userRequest.setIsModerator(user.getIsModerator());
        userRequest.setIsMerchant(user.getIsMerchant());
        userRequest.setAvatarImagePath(user.getAvatarImagePath());
        userRequest.setReputation(user.getReputation());
    
        return userRequest;
    }

    public List<UsersRequest> getAllUsers(Model model) {
        Long userId = (Long) model.getAttribute("userId");
        if (!repository.findById(userId).get().getIsAdmin())
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to view all users!");
        Iterable<Users> users = repository.findAll();
        List<UsersRequest> usersRequestList = new ArrayList<>();
    
        for (Users user : users) {
            UsersRequest usersRequest = new UsersRequest();
            usersRequest.setUsername(user.getUsername());
            usersRequest.setEmail(user.getEmail());
            usersRequest.setFirstName(user.getFirstName());
            usersRequest.setLastName(user.getLastName());
            usersRequest.setBirthdate(user.getBirthDate());
            usersRequest.setIsAdmin(user.getIsAdmin());
            usersRequest.setIsModerator(user.getIsModerator());
            usersRequest.setIsMerchant(user.getIsMerchant());
            usersRequest.setAvatarImagePath(user.getAvatarImagePath());
            usersRequest.setReputation(user.getReputation());
            usersRequest.setUserId(user.getUserId());
            usersRequestList.add(usersRequest);
        }
    
        usersRequestList.sort(Comparator.comparing(UsersRequest::getUserId));
    
        return usersRequestList;
    }

    public UsersProfileRequest getUserProfile(Long userId, Model model) {
        Optional<Users> response = repository.findById(userId);
        Users user = response.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    
        UsersProfileRequest userProfileRequest = new UsersProfileRequest();
        userProfileRequest.setUser(getUser(userId));
        userProfileRequest.setProducts(pservice.getUsersAllProducts(userId));
        userProfileRequest.setBlogPosts(bpservice.getUsersAllBlogPosts(userId));
        userProfileRequest.setReviews(rservice.getUsersAllReviews(userId));
        userProfileRequest.setBookmarkLists(blservice.getUsersAllBookmarkLists(userId, model));
        return userProfileRequest;
    }

    @SuppressWarnings("null")
    public void updateUser(Long userId, Users user, Model model) {
        Optional<Users> existingUser = repository.findById(userId);
        if (existingUser.isPresent() && model.getAttribute("userId").equals(existingUser.get().getUserId())){
            Users updatedUser = existingUser.get();
            updatedUser.setUsername(user.getUsername());
            updatedUser.setEmail(user.getEmail());
            updatedUser.setFirstName(user.getFirstName());
            updatedUser.setLastName(user.getLastName());
            updatedUser.setBirthDate(user.getBirthDate());
            updatedUser.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
            repository.save(updatedUser);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found or you are not authorized to update this user");
        }
    }

    @SuppressWarnings("null")
    public void deleteUser(Long userId, Model model) {
        if (repository.existsById(userId) && model.getAttribute("userId").equals(userId))
            repository.deleteById(userId);
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found or you are not authorized to delete this user");
    }

    @SuppressWarnings("null")
    public String getBalance(Model model) {
        Long userId = (Long) model.getAttribute("userId");
        return balanceRepository.findById(userId).get().getBalance().toString();
    }
}