package com.example.ctca.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class CharityDonationDTO {

    private long id;
    private String note;
    private String image;
    private String date;
    private String progress;

    // owner
    private AccountDTO account;
    private long accountId;

    // charity
    private long charityId;
    private CharityDTO charity;

    private MultipartFile avatarMul;

}
