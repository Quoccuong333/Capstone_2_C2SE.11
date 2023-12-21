package com.example.ctca.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDTO extends BaseDTO{

    private long id;
    private String content;
    private boolean status;
    private String date;

    // owner
    private AccountDTO accountDTO;
    private long accountId;

    //Post
    private PostDTO post;
    private long postId;

}
