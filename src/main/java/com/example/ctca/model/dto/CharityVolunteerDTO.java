package com.example.ctca.model.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CharityVolunteerDTO {

    private long id;
    private String note;
    private String progress;
    private boolean status;

    // owner
    private AccountDTO account;
    private long accountId;

    // charity
    private long charityId;
    private CharityDTO charity;

}
