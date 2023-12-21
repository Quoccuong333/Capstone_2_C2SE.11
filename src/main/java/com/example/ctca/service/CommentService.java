package com.example.ctca.service;

import com.example.ctca.model.entity.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> findAll();

    Comment findById(long id);

    Comment save(Comment comment);

    List<Comment> findByPostId(long postId);

    List<Comment> findByAccountIdAndPostId(long accountId, long postId);


}
