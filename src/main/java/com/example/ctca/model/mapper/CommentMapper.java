package com.example.ctca.model.mapper;

import com.example.ctca.model.dto.CommentDTO;
import com.example.ctca.model.entity.Comment;

import java.util.List;

public interface CommentMapper {

    CommentDTO toDTO(Comment comment);

    List<CommentDTO> toListDTO(List<Comment> commentList);

    Comment toEntity(CommentDTO commentDTO);
}
