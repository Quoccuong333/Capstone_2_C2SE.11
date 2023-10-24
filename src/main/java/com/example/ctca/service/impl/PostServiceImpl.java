package com.example.ctca.service.impl;

import com.example.ctca.model.entity.Category;
import com.example.ctca.model.entity.Post;
import com.example.ctca.repository.PostRepository;
import com.example.ctca.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    PostRepository postRepository;

    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public Post findById(long id) {
        return postRepository.findById(id).orElse(null);
    }

    @Override
    public Post save(Post post) {
        return postRepository.save(post);
    }


    @Override
    public Page<Post> findByCategoryAndStatusTrue(Category category, Pageable pageable) {

        Page<Post> pageResult = null;
        if (category == null) {
            pageResult = postRepository.findByProgress(pageable, "APPROVED");
        } else {
            pageResult = postRepository.findByCategoryAndProgress(category, "APPROVED", pageable);
        }
        return pageResult;
    }

    @Override
    public List<Post> findByCategory(Category category) {
        return postRepository.findByCategory(category);
    }

    @Override
    public List<Post> findByRelated(Post post, Category category, int limit) {
        return postRepository.findByRelated(post.getId(), category.getId(), limit);
    }
}
