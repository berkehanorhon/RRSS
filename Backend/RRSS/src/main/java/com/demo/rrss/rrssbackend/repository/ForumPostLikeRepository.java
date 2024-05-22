package com.demo.rrss.rrssbackend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.demo.rrss.rrssbackend.entity.ForumPostLike;

@Repository
public interface ForumPostLikeRepository extends CrudRepository<ForumPostLike, Long>{
    public Integer getCountByForumPostId(Long forumPostId);
}
