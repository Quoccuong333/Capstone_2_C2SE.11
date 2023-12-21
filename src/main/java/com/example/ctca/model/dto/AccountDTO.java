package com.example.ctca.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountDTO {
    private long id;
    private String fullName;
    private String email;
    private String password;
    private String phone;
    private boolean status;

    private RoleDTO roleDTO;
    private long roleId;
    private String roleName;

    private String confirmPassword;

    private String rePassword;
    private String oldPassword;
    private String newPassword;
    private String verifyNewPassword;

}
