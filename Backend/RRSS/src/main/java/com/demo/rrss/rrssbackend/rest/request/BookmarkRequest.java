package com.demo.rrss.rrssbackend.rest.request;

import java.sql.Timestamp;


import com.demo.rrss.rrssbackend.entity.Product;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class BookmarkRequest {
    private Long bookmarkId;

    private Long productId;

    private Long bookmarkListId;

    private Timestamp creationDate;

    private Product product;
}
