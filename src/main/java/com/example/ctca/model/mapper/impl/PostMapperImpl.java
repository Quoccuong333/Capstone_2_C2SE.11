package com.example.ctca.model.mapper.impl;

import com.example.ctca.model.dto.PostDTO;
import com.example.ctca.model.entity.Post;
import com.example.ctca.model.mapper.AccountMapper;
import com.example.ctca.model.mapper.CategoryMapper;
import com.example.ctca.model.mapper.PostMapper;
import com.example.ctca.service.AccountService;
import com.example.ctca.service.CategoryService;
import com.example.ctca.service.PostService;
import com.example.ctca.utils.FormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class PostMapperImpl implements PostMapper {

    @Autowired
    AccountMapper accountMapper;

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    PostService postService;

    @Autowired
    AccountService accountService;

    @Autowired
    CategoryService categoryService;


    @Override
    public PostDTO toDTO(Post post) {
        if (post == null){
            return null;
        }

        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setName(post.getName());
        postDTO.setType(post.getType());
        postDTO.setDescription(post.getDescription());
        postDTO.setPrice(FormatUtils.formatNumber(post.getPrice()));
        postDTO.setImage(post.getImage());
        postDTO.setNote(post.getNote());
        postDTO.setProgress(post.getProgress());
        postDTO.setStatus(post.isStatus());

        if (post.getOwner() != null) {
            postDTO.setOwnerId(post.getOwner().getId());
            postDTO.setOwnerDTO(accountMapper.toDTO(post.getOwner()));
        }
        if (post.getCategory() != null) {
            postDTO.setCategoryId(post.getCategory().getId());
            postDTO.setCategoryDTO(categoryMapper.toDTO(post.getCategory()));
        }

        switch (post.getType()) {
            case "EXCHANGE" -> postDTO.setColorType("full-time");
            case "PURCHASE" -> postDTO.setColorType("part-time");
            default -> postDTO.setColorType("freelanc");
        }

        return postDTO;
    }

    @Override
    public List<PostDTO> toListDTO(List<Post> postList) {
        if (postList == null) {
            return null;
        }
        List<PostDTO> list = new ArrayList<>(postList.size());
        for (Post post : postList) {
            PostDTO postDTO = toDTO(post);
            if (postDTO != null) {
                list.add(postDTO);
            }
        }
        return list;
    }

    @Override
    public Post toEntity(PostDTO postDTO) {
        if (postDTO == null)
            return null;

        Post post = postService.findById(postDTO.getId());
        if (post == null)
            post = new Post();

        post.setName(postDTO.getName());
        post.setType(postDTO.getType());
        post.setDescription(postDTO.getDescription());
        post.setPrice(FormatUtils.formatNumber(postDTO.getPrice()));
        if (!StringUtils.isEmpty(postDTO.getImage())) {
            post.setImage(postDTO.getImage());
        }
        post.setNote(postDTO.getNote());
        post.setProgress(postDTO.getProgress());
        post.setStatus(postDTO.isStatus());
        post.setOwner(accountService.findById(postDTO.getOwnerId()));
        post.setCategory(categoryService.findById(postDTO.getCategoryId()));

        return post;
    }
}
