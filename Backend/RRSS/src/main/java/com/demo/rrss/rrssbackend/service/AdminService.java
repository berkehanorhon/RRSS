package com.demo.rrss.rrssbackend.service;

import java.util.ArrayList;
import java.util.Comparator;
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
import com.demo.rrss.rrssbackend.repository.CategoryRepository;
import com.demo.rrss.rrssbackend.repository.ForumPostLikeRepository;
import com.demo.rrss.rrssbackend.repository.ForumPostRepository;
import com.demo.rrss.rrssbackend.repository.ForumReplyRepository;
import com.demo.rrss.rrssbackend.repository.ForumRepository;
import com.demo.rrss.rrssbackend.repository.ProductRatingRepository;
import com.demo.rrss.rrssbackend.repository.ProductRepository;
import com.demo.rrss.rrssbackend.repository.ReviewRepository;
import com.demo.rrss.rrssbackend.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.server.ResponseStatusException;
import com.demo.rrss.rrssbackend.rest.request.PermissionRequest;
import com.demo.rrss.rrssbackend.rest.request.UsersRequest;
import com.demo.rrss.rrssbackend.rest.request.UsersRequestAdmin;

import jakarta.transaction.Transactional;


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
    ProductRatingRepository ratingRepository;

    @Autowired 
    UsersService userService;

    @Autowired
    CategoryRepository categoryRepository;

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

    public void deleteCategory(Long categoryId, Model model){
        Long userId = (Long) model.getAttribute("userId");
        Optional<Users> existingUser = userRepository.findById(userId);
        if(existingUser.get().getIsAdmin())
            categoryRepository.deleteById(categoryId);
        else 
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You don't have permission to delete this post!");
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

    @Transactional
    public void deleteReview(Long reviewId, Model model){
        Long userId = (Long) model.getAttribute("userId");
        Optional<Users> existingUser = userRepository.findById(userId);
        if(existingUser.get().getIsAdmin()){
            Optional<Review> existingReview = reviewRepository.findById(reviewId);
            ratingRepository.deleteByUserIdAndProductId(existingReview.get().getUserId(), existingReview.get().getProductId());
            reviewRepository.deleteByReviewId(reviewId);
        }
        else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You don't have permission to delete this review!");
        }
    }

        public List<UsersRequestAdmin> getAllUsers(Model model) {
        Long userId = (Long) model.getAttribute("userId");
        if (!userRepository.findById(userId).get().getIsAdmin())
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to view all users!");
        Iterable<Users> users = userRepository.findAll();
        List<UsersRequestAdmin> usersRequestList = new ArrayList<>();
    
        for (Users user : users) {
            UsersRequestAdmin usersRequest = new UsersRequestAdmin();
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
            usersRequest.setIsBanned(user.getIsBanned());
            usersRequestList.add(usersRequest);
        }
    
        usersRequestList.sort(Comparator.comparing(UsersRequestAdmin::getUserId));
    
        return usersRequestList;
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

    public void updateUserPermission(PermissionRequest request, Model model) {  
        Long userId = (Long) model.getAttribute("userId");
        Optional<Users> existingUser = userRepository.findById(userId);
        if(request.getUserId() == userId){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can't change your own permissions!");
        }
        if(request.getUserId() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User ID is missing!");
        }
        if(existingUser.get().getIsAdmin()){
            Optional<Users> userToBeUpdated = userRepository.findById(request.getUserId());

            if(request.isSetAdmin() == true)
                userToBeUpdated.get().setIsAdmin(true);
            else if(request.isSetAdmin() == false)
                userToBeUpdated.get().setIsAdmin(false);
            if(request.isSetMerchant() == true)
                userToBeUpdated.get().setIsMerchant(true);
            else if(request.isSetMerchant() == false)
                userToBeUpdated.get().setIsMerchant(false);
            if(request.isSetModerator() == true)
                userToBeUpdated.get().setIsModerator(true);
            else if(request.isSetModerator() == false)
                userToBeUpdated.get().setIsModerator(false);
            if(request.isSetBanUser() == true)
                userToBeUpdated.get().setIsBanned(true);
            else if(request.isSetBanUser() == false)
                userToBeUpdated.get().setIsBanned(false);
            
            userRepository.save(userToBeUpdated.get());
        }        
        else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You don't have permission to set user roles!");
        } 

    }

}
