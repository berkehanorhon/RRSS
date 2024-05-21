package com.demo.rrss.rrssbackend.repository;

import com.demo.rrss.rrssbackend.entity.Bookmark;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookmarkRepository extends CrudRepository<Bookmark, Long> {
    List<Bookmark> findByBookmarkListId(Long bookmarkListId);
    Bookmark findByBookmarkListIdAndProductId(Long bookmarkListId, Long productId);

    @Query("SELECT b.productId FROM Bookmark b WHERE b.bookmarkListId = :bookmarkListId")
    List<Long> findProductIdsByBookmarkListId(@Param("bookmarkListId") Long bookmarkListId);
}
