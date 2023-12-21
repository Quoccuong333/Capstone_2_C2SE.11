package com.example.ctca.controller.front;

import com.example.ctca.model.dto.AccountDTO;
import com.example.ctca.model.dto.MessageDTO;
import com.example.ctca.model.entity.Account;
import com.example.ctca.model.mapper.AccountMapper;
import com.example.ctca.service.AccountService;
import com.example.ctca.utils.ObjectUtil;
import com.example.ctca.utils.ValidatorUtil;
import com.example.ctca.validator.PasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Controller
@RequestMapping(value = {"/change/form"})
public class ChangePassController {
    private static final String REDIRECT_URL = "/change/form";

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private PasswordValidator passwordValidator;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private ValidatorUtil validatorUtil;

    @GetMapping(value = {""})
    public String view(Model model) {
        try {
            model.addAttribute("accountDTO", new AccountDTO());
            model.addAttribute("errorList", new HashMap<>());

            return "front/password_form";
        } catch (Exception ex) {
            ex.printStackTrace();
            return "redirect:" + REDIRECT_URL;
        }
    }

    @PostMapping(value = "")
    public String save(Model model, AccountDTO accountDTO, Authentication authentication, BindingResult bindingResult, @RequestParam(required = false) String action,
                       @RequestParam(required = false) String status) {
        try {
            Account account = ObjectUtil.getAccount(authentication);
            Account accountDB = accountService.findById(account.getId());
            accountDTO.setId(account.getId());
            if (account == null) {
                return "/front/password_form";
            }
            // verify value
            passwordValidator.validate(accountDTO, bindingResult);

            if (bindingResult.hasErrors()) {
                model.addAttribute("errorList", validatorUtil.toErrors(bindingResult.getFieldErrors()));
                model.addAttribute("messageDTO", new MessageDTO("save", "warning",
                        "Vui lòng kiểm tra lại thông tin!"));
                return "/front/password_form";
            } else {
                // save
                String encodedPassword = passwordEncoder.encode(accountDTO.getNewPassword());
                accountDB.setPassword(encodedPassword);
                accountService.save(accountDB);
                String redirectUrl = "/login" + "?action=save&status=success";

                return "redirect:" + redirectUrl;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return "redirect:" + REDIRECT_URL;
        }
    }
}
