package com.example.ctca.handler;

import com.example.ctca.config.custom.CustomUserDetails;
import com.example.ctca.model.dto.AccountDTO;
import com.example.ctca.model.entity.Account;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice(annotations = Controller.class)
public class UserLoginHandler {

    @ModelAttribute("userLogin")
    public AccountDTO getCurrentUser(Authentication authentication) {
        if (authentication == null) {
            return null;
        }

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Account account = null;

        if (customUserDetails != null) {
            account = customUserDetails.getAccount();
        }

        AccountDTO accountDTO = null;
        if (account != null) {
            accountDTO = new AccountDTO();
            accountDTO.setFullName(account.getFullName());
            accountDTO.setEmail(account.getEmail());
            accountDTO.setPhone(account.getPhone());
            accountDTO.setRoleName(account.getRole().getName());
        }
        return accountDTO;
    }

    @ModelAttribute("requestURI")
    public String requestURI(final HttpServletRequest request) {
        return request.getRequestURI();
    }

    @ModelAttribute("requestURL")
    public String requestURL(final HttpServletRequest request) {
        return request.getRequestURL().toString();
    }

}
