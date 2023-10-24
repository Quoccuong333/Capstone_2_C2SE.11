package com.example.ctca.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CharityVolunteerDTO {

    private long id;
    private String note;
    private String progress;

    // owner
    private AccountDTO account;

    // charity
    private long charityId;

}
