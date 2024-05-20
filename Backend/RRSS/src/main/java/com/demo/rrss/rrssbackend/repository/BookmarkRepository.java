package com.demo.rrss.rrssbackend.repository;

import com.demo.rrss.rrssbackend.entity.Bookmark;
import org.springframework.data.repository.CrudRepository;

import java.awt.print.Book;
import java.util.List;

public interface BookmarkRepository extends CrudRepository<Bookmark, Long> {
    List<Bookmark> findByBookmarkListId(Long bookmarkListId);
    Bookmark findByBookmarkListIdAndProductId(Long bookmarkListId, Long productId);
}
