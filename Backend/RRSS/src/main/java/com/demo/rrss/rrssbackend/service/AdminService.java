package com.demo.rrss.rrssbackend.service;

import java.util.List;
import java.util.Optional;

import com.demo.rrss.rrssbackend.entity.BlogPost;
import com.demo.rrss.rrssbackend.entity.Forum;
import com.demo.rrss.rrssbackend.entity.ForumPost;
import com.demo.rrss.rrssbackend.entity.Product;
import com.demo.rrss.rrssbackend.entity.Review;
import com.demo.rrss.rrssbackend.entity.Users;
import com.demo.rrss.rrssbackend.repository.BlogPostLikeRepository;
import com.demo.rrss.rrssbackend.repository.BlogPostRepository;
import com.demo.rrss.rrssbackend.repository.ForumPostLikeRepository;
import com.demo.rrss.rrssbackend.repository.ForumPostRepository;
import com.demo.rrss.rrssbackend.repository.ForumReplyRepository;
import com.demo.rrss.rrssbackend.repository.ForumRepository;
import com.demo.rrss.rrssbackend.repository.ProductRepository;
import com.demo.rrss.rrssbackend.repository.ReviewRepository;
import com.demo.rrss.rrssbackend.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.server.ResponseStatusException;
import com.demo.rrss.rrssbackend.rest.request.PermissionRequest;


@Service
public class AdminService {

    @Autowired
    UsersRepository userRepository;

    @Autowired
    ProductService productService;
    @Autowired
    ProductRepository productRepository;

    @Autowired
    ReviewService reviewService;
    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    BlogPostService blogPostService;
    @Autowired
    BlogPostRepository blogPostRepository;

    @Autowired 
    ForumPostService forumPostService;
    @Autowired
    ForumPostRepository forumPostRepository;

    @Autowired 
    ForumService forumService;
    @Autowired
    ForumRepository forumRepository;

    @Autowired
    ForumPostLikeRepository forumPostLikeRepository;

    @Autowired 
    BlogPostLikeRepository blogPostLikeRepository;

    @Autowired
    ForumReplyRepository forumReplyRepository;

    @Autowired 
    UsersService userService;


    public void deleteForum(Long forumId, Model model){
        Long userId = (Long) model.getAttribute("userId");
        Optional<Users> existingUser = userRepository.findById(userId);
        if(existingUser.get().getIsAdmin()){
            forumReplyRepository.deleteByForumId(forumId);
            forumPostRepository.deleteByForumId(forumId);
            forumRepository.deleteById(forumId);
        }
        else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You don't have permission to delete this forum!");
        }
    }

    public void deleteForumPost(Long forumPostId, Model model){
        Long userId = (Long) model.getAttribute("userId");
        Optional<Users> existingUser = userRepository.findById(userId);
        if(existingUser.get().getIsAdmin()){
            forumPostLikeRepository.deleteByForumPostId(forumPostId);
            forumPostRepository.deleteById(forumPostId);
        }
        else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You don't have permission to delete this post!");
        }
    }

    public void deleteBlogPost(Long blogPostId, Model model){
        Long userId = (Long) model.getAttribute("userId");
        Optional<Users> existingUser = userRepository.findById(userId);
        if(existingUser.get().getIsAdmin()){
            blogPostLikeRepository.deleteByBlogPostId(blogPostId);
            blogPostRepository.deleteById(blogPostId);
        }
        else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You don't have permission to delete this post!");
        }
    }

    public void deleteProduct(Long productId, Model model){
        Long userId = (Long) model.getAttribute("userId");
        Optional<Users> existingUser = userRepository.findById(userId);
        if(existingUser.get().getIsAdmin()){
            productRepository.deleteByProductId(productId);
        }
        else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You don't have permission to delete this product!");
        }
    }

    public void deleteReview(Long reviewId, Model model){
        Long userId = (Long) model.getAttribute("userId");
        Optional<Users> existingUser = userRepository.findById(userId);
        if(existingUser.get().getIsAdmin()){
            reviewRepository.deleteByReviewId(reviewId);
        }
        else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You don't have permission to delete this review!");
        }
    }

    public List<Users> getAllUsers(Model model){
        Long userId = (Long) model.getAttribute("userId");
        Optional<Users> existingUser = userRepository.findById(userId);
        if(existingUser.get().getIsAdmin()){
            List<Users> allUsers = (List<Users>) userRepository.findAll();
            return allUsers;
        }
        else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You don't have permission to get all users!");
        }
    }

    public List<Review> getAllReviews(Model model){
        Long userId = (Long) model.getAttribute("userId");
        Optional<Users> existingUser = userRepository.findById(userId);
        if(existingUser.get().getIsAdmin()){
            List<Review> allReviews = (List<Review>) reviewRepository.findAll();
            return allReviews;
        }
        else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You don't have permission to get all reviews!");
        }
    }

    public List<Product> getAllProducts(Model model){
        Long userId = (Long) model.getAttribute("userId");
        Optional<Users> existingUser = userRepository.findById(userId);
        if(existingUser.get().getIsAdmin()){
            List<Product> allProducts = (List<Product>) productRepository.findAll();
            return allProducts;
        }
        else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You don't have permission to get all products!");
        }
    }

    public List<Forum> getAllForums(Model model){
        Long userId = (Long) model.getAttribute("userId");
        Optional<Users> existingUser = userRepository.findById(userId);
        if(existingUser.get().getIsAdmin()){
            List<Forum> allForums = (List<Forum>) forumRepository.findAll();
            return allForums;
        }
        else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You don't have permission to get all forums!");
        }
    }

    public List<ForumPost> getAllForumPosts(Model model){
        Long userId = (Long) model.getAttribute("userId");
        Optional<Users> existingUser = userRepository.findById(userId);
        if(existingUser.get().getIsAdmin()){
            List<ForumPost> allForumPosts = (List<ForumPost>) forumPostRepository.findAll();
            return allForumPosts;
        }
        else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You don't have permission to get all forum posts!");
        }
    }

    public List<BlogPost> getAllBlogPosts(Model model){
        Long userId = (Long) model.getAttribute("userId");
        Optional<Users> existingUser = userRepository.findById(userId);
        if(existingUser.get().getIsAdmin()){
            List<BlogPost> allBlogPosts = (List<BlogPost>) blogPostRepository.findAll();
            return allBlogPosts;
        }
        else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You don't have permission to get all blog posts!");
        }
    }

    public void updateUserPermission(Long userIdToBeUpdated, PermissionRequest request, Model model) {  
        Long userId = (Long) model.getAttribute("userId");
        Optional<Users> existingUser = userRepository.findById(userId);

        if(existingUser.get().getIsAdmin()){
            Optional<Users> userToBeUpdated = userRepository.findById(userIdToBeUpdated);

            if(request.isSetAdmin() == true)
                userToBeUpdated.get().setIsAdmin(true);
            else if(request.isSetAdmin() == false)
                userToBeUpdated.get().setIsAdmin(false);
            else if(request.isSetMerchant() == true)
                userToBeUpdated.get().setIsMerchant(true);
            else if(request.isSetMerchant() == false)
                userToBeUpdated.get().setIsMerchant(false);
            else if(request.isSetModerator() == true)
                userToBeUpdated.get().setIsModerator(true);
            else if(request.isSetModerator() == false){
                userToBeUpdated.get().setIsModerator(false); 
            }
            userRepository.save(userToBeUpdated.get());
        }
        else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You don't have permission to set user roles!");
        }
    }

}
