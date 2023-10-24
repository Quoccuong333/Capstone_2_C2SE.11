package com.example.ctca.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDTO {
    private long id;
    private String name;
    private String description;

    private int amountPost;

    private boolean status;

}
