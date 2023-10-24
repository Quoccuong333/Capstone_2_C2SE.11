package com.example.ctca.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Getter
@Setter
public class CharityDTO {

    private long id;
    private String title;
    private String image;
    private String description;
    private String note;
    private String startDate;
    private String endDate;
    private String progress; // PENDING || CANCELED || APPROVED || COMPLETED

    private MultipartFile avatarMul;

    private boolean status;

    // owner
    private AccountDTO owner;

}
