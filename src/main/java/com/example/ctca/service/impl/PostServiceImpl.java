package com.example.ctca.service.impl;

import com.example.ctca.model.dto.ReportDTO;
import com.example.ctca.model.entity.Account;
import com.example.ctca.model.entity.Category;
import com.example.ctca.model.entity.Post;
import com.example.ctca.repository.PostRepository;
import com.example.ctca.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
    public List<Post> findByOwner(long accountId) {
        Account account = new Account();
        account.setId(accountId);

        return postRepository.findByOwner(account);
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
    public Page<Post> findByCategoryAndStatusTrueAndNameContains(Category category, Pageable pageable, String title) {
        Page<Post> pageResult = null;
        if (category == null && StringUtils.isEmpty(title)) {
            pageResult = postRepository.findByProgress(pageable,"APPROVED");
        } else if (category != null && StringUtils.isEmpty(title)) {
            pageResult = postRepository.findByCategoryAndProgress(category, "APPROVED",pageable);
        } else if (category == null && !StringUtils.isEmpty(title)) {
            pageResult = postRepository.findByProgressAndNameContains("APPROVED", pageable, title);
        } else {
            pageResult = postRepository.findByCategoryAndProgressAndNameContains(category, "APPROVED", pageable, title);
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

    @Override
    public List<Post> findByProgress(String progress) {
        return postRepository.findByProgress(progress);
    }

    @Override
    public List<Post> findByStatusIsTrue() {
        return postRepository.findByStatusIsTrue();
    }

    @Override
    public List<Post> findReport(ReportDTO reportDTO) {
        String startDate = reportDTO.getStartDate() + " 00:00:00";
        String endDate = reportDTO.getEndDate() +  " 23:59:59";
        return postRepository.findReport(startDate, endDate);
    }
}
