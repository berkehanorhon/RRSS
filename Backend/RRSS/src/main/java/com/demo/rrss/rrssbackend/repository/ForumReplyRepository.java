package com.demo.rrss.rrssbackend.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.demo.rrss.rrssbackend.entity.ForumReply;

@Repository
public interface ForumReplyRepository extends CrudRepository<ForumReply, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM ForumReply fr WHERE fr.forumPostId = :forumPostId")
    public void deleteByForumPostId(Long forumPostId);

    @Modifying
    @Transactional
    @Query("DELETE FROM ForumReply fr WHERE fr.forumPostId IN (SELECT fp.forumId FROM ForumPost fp WHERE fp.forumId = :forumId)")
    public void deleteByForumId(Long forumId);
    

}
