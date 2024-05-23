package com.demo.rrss.rrssbackend.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.demo.rrss.rrssbackend.entity.BlogPost;
import com.demo.rrss.rrssbackend.entity.ForumPost;
import com.demo.rrss.rrssbackend.entity.Product;
import com.demo.rrss.rrssbackend.entity.Review;
import com.demo.rrss.rrssbackend.entity.Users;
import com.demo.rrss.rrssbackend.rest.request.PermissionRequest;
import com.demo.rrss.rrssbackend.service.AdminService;
import com.demo.rrss.rrssbackend.service.JwtUtil;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
	private JwtUtil jwtUtil;

    @Autowired
    AdminService adminService;

	@ModelAttribute // TODO Herhangi bir hata durumunda 403 döndürülecek
	public void addUserIdToModel(@RequestHeader(value="Authorization") String bearerToken, Model model) {
		String token = bearerToken.substring(7);
		Long userId = jwtUtil.extractUserId(token);
		model.addAttribute("userId", userId);
	}


    @DeleteMapping(value = "/delete-forum")
	@ResponseStatus(HttpStatus.OK)
    public void deleteForum(@RequestParam Long forumId, Model model){
        adminService.deleteForum(forumId, model);
    }


    @DeleteMapping(value = "/delete-forum-post")
	@ResponseStatus(HttpStatus.OK)
    public void deleteForumPost(@RequestParam Long forumPostId, Model model){
        adminService.deleteForumPost(forumPostId, model);
    }


    @DeleteMapping(value = "/delete-blog-post")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBlogPost(@RequestParam Long blogPostId, Model model){
        adminService.deleteBlogPost(blogPostId, model);
    }


    @DeleteMapping(value = "/delete-product")
	@ResponseStatus(HttpStatus.OK)
    public void deleteProduct(@RequestParam Long productId, Model model){
        adminService.deleteProduct(productId, model);
    }


    @DeleteMapping(value = "/delete-review")
	@ResponseStatus(HttpStatus.OK)
    public void deleteReview(@RequestParam Long reviewId, Model model){
        adminService.deleteReview(reviewId, model);
    }


    @GetMapping(value = "/get-all-users")
	@ResponseStatus(HttpStatus.OK)
    public List<Users> getAllUsers(Model model){
        List<Users> allUsers = adminService.getAllUsers(model);
        return allUsers.stream().limit(50).collect(Collectors.toList()); //TODO can arrange maxSize
    }


    @GetMapping(value = "/get-all-products")
	@ResponseStatus(HttpStatus.OK)
    public List<Product> getAllProducts(Model model){
        List<Product> allProducts = adminService.getAllProducts(model);
        return allProducts.stream().limit(50).collect(Collectors.toList()); //TODO can arrange maxSize
    }


    @GetMapping(value = "/get-all-reviews")
	@ResponseStatus(HttpStatus.OK)
    public List<Review> getAllReviews(Model model){
        List<Review> allReviews = adminService.getAllReviews(model);
        return allReviews.stream().limit(50).collect(Collectors.toList()); //TODO can arrange maxSize
    }


    @GetMapping(value = "/get-all-forum-posts")
	@ResponseStatus(HttpStatus.OK)
    public List<ForumPost> getAllForumPosts(Model model){
        List<ForumPost> allForumPosts = adminService.getAllForumPosts(model);
        return allForumPosts.stream().limit(50).collect(Collectors.toList()); //TODO can arrange maxSize
    }


    @GetMapping(value = "/get-all-blog-posts")
	@ResponseStatus(HttpStatus.OK)
    public List<BlogPost> getAllBlogPosts(Model model){
        List<BlogPost> allBlogPosts = adminService.getAllBlogPosts(model);
        return allBlogPosts.stream().limit(50).collect(Collectors.toList()); //TODO can arrange maxSize
    }
    

    @PatchMapping(value = "/update-user")
    @ResponseStatus(HttpStatus.OK)
    public void updateUserPermission(@RequestParam Long userId, @RequestBody PermissionRequest request, Model model){
        adminService.updateUserPermission(userId, request, model);
    }

}
