package com.example.ctca.repository;

import com.example.ctca.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    //comment for user
    List<Comment> findByAccountIdAndPostId(long accountId, long postId);

    //comment for owner
    List<Comment> findByPostId(long postId);

}
