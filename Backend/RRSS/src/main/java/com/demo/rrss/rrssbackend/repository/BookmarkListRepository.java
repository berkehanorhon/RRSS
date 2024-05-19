package com.demo.rrss.rrssbackend.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.demo.rrss.rrssbackend.entity.BookmarkList;

@Repository
public interface BookmarkListRepository extends CrudRepository<BookmarkList, Long> {
    List<BookmarkList> findByUserId(Long userId);
}
