package com.example.ctca.controller;

import com.example.ctca.model.dto.AccountDTO;
import com.example.ctca.model.dto.MessageDTO;
import com.example.ctca.model.entity.Account;
import com.example.ctca.service.AccountService;
import com.example.ctca.validator.RegisterValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private static final String REDIRECT_URL = "/register";

    @Autowired
    private AccountService accountService;

    @Autowired
    private RegisterValidator registerValidator;

    @GetMapping(value = {"", "/"})
    public String show(Model model) {
        try {
            AccountDTO accountDTO = new AccountDTO();

            model.addAttribute("messageDTO", null);
            model.addAttribute("accountDTO", accountDTO);

            return "register";
        } catch (Exception ex) {
            return "redirect:" + REDIRECT_URL;
        }
    }

    @PostMapping("")
    public String register(Model model, @Valid AccountDTO accountDTO, BindingResult bindingResult) {
        try {
            // validate
            registerValidator.validate(accountDTO, bindingResult);

            if (bindingResult.hasErrors()) {
                model.addAttribute("messageDTO", new MessageDTO("save", "warning",
                        "Vui lòng kiểm tra lại thông tin!"));
                model.addAttribute("accountDTO", accountDTO);
                return "register";
            }
            // save
            Account account = accountService.register(accountDTO);
            String redirectUrl = "/register/success?email=" + account.getEmail();
            return "redirect:" + redirectUrl;
        } catch (Exception ex) {
            return "redirect:" + REDIRECT_URL;
        }
    }

    @GetMapping(value = {"/success", "/success/"})
    public String registerSuccess(Model model, @RequestParam(required = false) String email) {
        try {
            AccountDTO accountDTO = new AccountDTO();
            accountDTO.setEmail(email);

            model.addAttribute("messageDTO", null);
            model.addAttribute("accountDTO", accountDTO);

            return "register_success";
        } catch (Exception ex) {
            return "redirect:" + REDIRECT_URL;
        }
    }

}
