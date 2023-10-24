package com.example.ctca.model.mapper;

import com.example.ctca.model.dto.PostDTO;
import com.example.ctca.model.entity.Post;

import java.util.List;

public interface PostMapper {

    PostDTO toDTO(Post post);

    List<PostDTO> toListDTO(List<Post> postList);

    Post toEntity(PostDTO postDTO);
}
