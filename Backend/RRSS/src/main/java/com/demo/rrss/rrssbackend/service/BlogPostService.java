package com.demo.rrss.rrssbackend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.ui.Model;
import com.demo.rrss.rrssbackend.entity.BlogPost;
import com.demo.rrss.rrssbackend.repository.BlogPostRepository;
// import com.demo.rrss.rrssbackend.rest.request.BlogPostRequest;

@Service
public class BlogPostService {
    @Autowired
    BlogPostRepository repository;

    // public BlogPost getBlogPost(Long blogPostId) {
    //     return repository.findById(blogPostId)
    //             .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Blog post not found"));
    // }

    // public void addBlogPost(BlogPostRequest request, Model model) { // TODO yetki kontrolü eklenecek
    //     Long userId = (Long) model.getAttribute("userId"); 
    //     BlogPost blogPost = new BlogPost();
    //     blogPost.setCategoryId(request.getCategoryId());
    //     blogPost.setDescription(request.getDescription());
    //     blogPost.setPublishDate(new java.sql.Timestamp(new java.util.Date().getTime()));

    //     blogPost.setTitle(request.getTitle());
    //     blogPost.setUserId(userId);
    //     repository.save(blogPost);
    // }

    // public void updateBlogPost(Long blogPostId, BlogPostRequest request) { // TODO yetki kontrolü eklenecek
    //     Optional<BlogPost> existingBlogPost = repository.findById(blogPostId);
    //     if (existingBlogPost.isPresent()) {
    //         BlogPost blogPost = existingBlogPost.get();
    //         blogPost.setCategoryId(request.getCategoryId());
    //         blogPost.setDescription(request.getDescription());
    //         blogPost.setPublishDate(new java.sql.Timestamp(new java.util.Date().getTime()));
    //         blogPost.setTitle(request.getTitle());
    //         blogPost.setUserId(request.getUserId());
    //         repository.save(blogPost);
    //     } else {
    //         throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Blog post not found");
    //     }
    // }

    // public void deleteBlogPost(Long blogPostId) { // TODO yetki kontrolü eklenecek
    //     if (repository.existsById(blogPostId))
    //         repository.deleteById(blogPostId);
    //     else
    //         throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Blog post not found");

    // }

    // public List<BlogPost> getAllBlogPosts(Long categoryId) { // TODO yetki kontrolü eklenecek
    //     if (categoryId == -1)
    //         return repository.findAllMax50();
    //     else {
    //         List<BlogPost> blogPosts = repository.findBlogPostsByCategoryId(categoryId);
    //         return blogPosts;
    //     }
    // }

    public List<BlogPost> getUsersAllBlogPosts(Long userId) {
        return repository.findByUserId(userId);
    }
}