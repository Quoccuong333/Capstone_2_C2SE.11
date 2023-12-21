package com.example.ctca.model.mapper.impl;

import com.example.ctca.model.dto.CommentDTO;
import com.example.ctca.model.entity.Comment;
import com.example.ctca.model.mapper.AccountMapper;
import com.example.ctca.model.mapper.CommentMapper;
import com.example.ctca.model.mapper.PostMapper;
import com.example.ctca.service.AccountService;
import com.example.ctca.service.CommentService;
import com.example.ctca.service.PostService;
import com.example.ctca.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CommentMapperImpl implements CommentMapper {

    @Autowired
    CommentService commentService;

    @Autowired
    AccountService accountService;

    @Autowired
    AccountMapper accountMapper;

    @Autowired
    PostService postService;

    @Autowired
    PostMapper postMapper;


    @Override
    public CommentDTO toDTO(Comment comment) {
        if (comment == null){
            return null;
        }
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setContent(comment.getContent());
        commentDTO.setStatus(comment.isStatus());
        commentDTO.setDate(DateUtil.convertDateToString(comment.getCreatedOn(), "dd/MM/yyyy"));

        //account
        if (comment.getAccount() != null){
            commentDTO.setAccountId(comment.getAccount().getId());
            commentDTO.setAccountDTO(accountMapper.toDTO(comment.getAccount()));
        }

        //post
        if (comment.getPost() != null){
            commentDTO.setPostId(comment.getPost().getId());
            commentDTO.setPost(postMapper.toDTO(comment.getPost()));
        }


        return commentDTO;
    }

    @Override
    public List<CommentDTO> toListDTO(List<Comment> commentList) {
        if (commentList == null) {
            return null;
        }
        List<CommentDTO> list = new ArrayList<>(commentList.size());
        for (Comment comment : commentList) {
            CommentDTO commentDTO = toDTO(comment);
            if (commentDTO != null) {
                list.add(commentDTO);
            }
        }
        return list;
    }

    @Override
    public Comment toEntity(CommentDTO commentDTO) {
        if (commentDTO == null)
            return null;

        Comment comment = commentService.findById(commentDTO.getId());
        if (comment == null)
            comment = new Comment();

        comment.setContent(commentDTO.getContent());
        comment.setStatus(commentDTO.isStatus());
        comment.setAccount(accountService.findById(commentDTO.getAccountId()));
        comment.setPost(postService.findById(commentDTO.getPostId()));

        return comment;
    }
}
