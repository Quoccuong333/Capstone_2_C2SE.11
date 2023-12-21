package com.example.ctca.model.dto;

import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
public class EmailAccountDTO {

    private long id;
    private String fullName;
    private String email;
    private String password;
    private String phone;

    private String[] contractId;
    private String accountRef;
    private String link;
    private String otp;
    private String ctca;

}
