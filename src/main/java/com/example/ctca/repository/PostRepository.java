package com.example.ctca.repository;

import com.example.ctca.model.entity.Category;
import com.example.ctca.model.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByCategory(Category category);

    Page<Post> findByProgress(Pageable pageable, String progress);

    Page<Post> findByCategoryAndProgress(Category category, String progress, Pageable pageable);

    Post findByName(String name);

    @Query(value = """
            SELECT * FROM course c\s
            WHERE c.id != :postId AND c.category_id = :categoryId AND c.status = TRUE\s
            ORDER BY RAND() LIMIT :limit""", nativeQuery = true)
    List<Post> findByRelated(long postId, long categoryId, int limit);

}
