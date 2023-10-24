package com.example.ctca.service;

import com.example.ctca.model.entity.Category;
import com.example.ctca.model.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {

    List<Post> findAll();

    Post findById(long id);

    Post save(Post post);

    Page<Post> findByCategoryAndStatusTrue(Category category, Pageable pageable);

    List<Post> findByCategory(Category category);

    List<Post> findByRelated(Post post, Category category, int limit);
}
