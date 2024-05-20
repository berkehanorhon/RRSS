package com.demo.rrss.rrssbackend.rest.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

import com.demo.rrss.rrssbackend.entity.Users;
import com.demo.rrss.rrssbackend.entity.Product;
import com.demo.rrss.rrssbackend.entity.Review;
import com.demo.rrss.rrssbackend.entity.BlogPost;
import com.demo.rrss.rrssbackend.entity.BookmarkList;

@Setter
@Getter
@Data
public class UsersProfileRequest {
    private UsersRequest user;
    private List<Product> products;
    private List<Review> reviews;
    private List<BlogPost> blogPosts;
    private List<BookmarkList> bookmarkLists;
}