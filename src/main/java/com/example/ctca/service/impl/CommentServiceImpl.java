package com.example.ctca.service.impl;

import com.example.ctca.model.entity.Comment;
import com.example.ctca.repository.CommentRepository;
import com.example.ctca.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepository commentRepository;


    @Override
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    public Comment findById(long id) {
        return commentRepository.findById(id).orElse(null);
    }

    @Override
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> findByPostId(long postId) {
        return commentRepository.findByPostId(postId);
    }

    @Override
    public List<Comment> findByAccountIdAndPostId(long accountId, long postId) {
        return commentRepository.findByAccountIdAndPostId(accountId, postId);
    }
}
