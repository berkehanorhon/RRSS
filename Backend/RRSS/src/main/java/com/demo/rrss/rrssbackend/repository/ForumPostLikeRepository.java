package com.demo.rrss.rrssbackend.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.demo.rrss.rrssbackend.entity.ForumPostLike;

@Repository
public interface ForumPostLikeRepository extends CrudRepository<ForumPostLike, Long>{
    
    @Query("SELECT COUNT(f) FROM ForumPostLike f WHERE f.forumPostId = :forumPostId")
    public Integer getCountByForumPostId(Long forumPostId);

    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END FROM ForumPostLike f WHERE f.forumPostId = :forumPostId")
    public boolean existsByForumPostId(Long forumPostId);
    
    @Modifying
    @Transactional
    @Query("DELETE FROM ForumPostLike f WHERE f.forumPostId = :forumPostId")
    public void deleteByForumPostId(Long forumPostId);
    
    @Query("SELECT f FROM ForumPostLike f WHERE f.forumPostId = :forumPostId")
    public ForumPostLike findByForumPostId(Long forumPostId);

}