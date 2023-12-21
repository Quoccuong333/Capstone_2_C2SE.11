package com.example.ctca.utils;

import com.example.ctca.config.custom.CustomUserDetails;
import com.example.ctca.model.entity.Account;
import org.springframework.security.core.Authentication;
import org.springframework.util.ObjectUtils;

public class ObjectUtil {

    public static Account getAccount(Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Account account = new Account();
        if (!ObjectUtils.isEmpty(customUserDetails)) {
            account = customUserDetails.getAccount();
        }
        return account;
    }

}
