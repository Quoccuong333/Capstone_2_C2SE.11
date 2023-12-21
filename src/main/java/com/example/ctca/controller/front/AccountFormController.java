package com.example.ctca.controller.front;

import com.example.ctca.config.custom.CustomUserDetails;
import com.example.ctca.model.dto.AccountDTO;
import com.example.ctca.model.entity.Account;
import com.example.ctca.model.mapper.AccountMapper;
import com.example.ctca.service.AccountService;
import com.example.ctca.validator.AccountValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping(value = {"/account/form"})
public class AccountFormController {
    private static final String REDIRECT_URL = "/account/form";

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private AccountValidator accountValidator;


    @GetMapping(value = {""})
    public String view(Model model, Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Account account = customUserDetails.getAccount();
        model.addAttribute("accountDTO", accountMapper.toDTO(account));

        return "front/account_form";
    }

    @PostMapping(value = "")
    public String save(Model model, Authentication authentication, AccountDTO accountDTO, BindingResult bindingResult) {
        try {
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            // validator
            accountValidator.validate(accountDTO, bindingResult);

            if (bindingResult.hasErrors()) {
                model.addAttribute("accountDTO", accountDTO);
                return "front/account_form";
            }

            Account account = accountMapper.toEntity(accountDTO);
            accountService.save(account);
            customUserDetails.setAccount(account);
            String redirectUrl = "/profile" + "?action=save&status=success&message=03";
            return "redirect:" + redirectUrl;
        } catch (Exception ex) {
            return "redirect:" + REDIRECT_URL;
        }
    }

}
