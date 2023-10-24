package com.example.ctca.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class PostDTO {

    private long id;
    private String type;
    private String colorType;
    private String name;
    private String description;
    private String price;
    private String image;
    private String note;
    private String progress;
    private boolean status;

    private AccountDTO ownerDTO;
    private long ownerId;

    private CategoryDTO categoryDTO;
    private long categoryId;

    private MultipartFile avatarMul;
}
