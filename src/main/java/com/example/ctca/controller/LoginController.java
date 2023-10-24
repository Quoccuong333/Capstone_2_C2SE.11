package com.example.ctca.controller;

import com.example.ctca.model.dto.AccountDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping(value = {"", "/"})
    public String showLogin(Model model) {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setEmail("");
        accountDTO.setPassword("");

        model.addAttribute("accountDTO", accountDTO);
        model.addAttribute("error", null);

        return "login";
    }

}
