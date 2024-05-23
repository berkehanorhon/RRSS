package com.demo.rrss.rrssbackend.repository;

import com.demo.rrss.rrssbackend.entity.Bookmark;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface BookmarkRepository extends CrudRepository<Bookmark, Long> {
    List<Bookmark> findByBookmarkListId(Long bookmarkListId);
    Bookmark findByBookmarkListIdAndProductId(Long bookmarkListId, Long productId);

    @Query("SELECT b.productId FROM Bookmark b WHERE b.bookmarkListId IN :bookmarkListIds")
    Set<Long> findProductIdsByBookmarkListIds(@Param("bookmarkListIds") List<Long> bookmarkListIds);
}
