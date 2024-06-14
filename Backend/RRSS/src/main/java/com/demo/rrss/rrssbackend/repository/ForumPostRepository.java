package com.demo.rrss.rrssbackend.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.demo.rrss.rrssbackend.entity.ForumPost;
import com.demo.rrss.rrssbackend.entity.ForumPostLike;

@Repository
public interface ForumPostRepository extends CrudRepository<ForumPost, Long>{
    public void deleteByForumPostId(long forumPostId);

    @Modifying
    @Transactional
    @Query("DELETE FROM ForumPost f WHERE f.forumId = :forumId")
    public void deleteByForumId(Long forumId);

    @Query("SELECT f FROM ForumPost f WHERE f.forumId = :forumId")
    public ForumPost findByForumId(Long forumId);

}
