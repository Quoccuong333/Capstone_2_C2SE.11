package com.example.ctca.controller.back;

import com.example.ctca.model.dto.AccountDTO;
import com.example.ctca.model.dto.MessageDTO;
import com.example.ctca.model.entity.Account;
import com.example.ctca.model.mapper.AccountMapper;
import com.example.ctca.model.mapper.RoleMapper;
import com.example.ctca.service.AccountService;
import com.example.ctca.service.RoleService;
import com.example.ctca.utils.ValidatorUtil;
import com.example.ctca.validator.AccountValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/back/account")
public class AccountController {

    private String redirectUrl = "/back/account";

    @Autowired
    private AccountService accountService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private AccountValidator accountValidator;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping(value = {"", "/"})
    public String list(Model model) {
        try {
            List<Account> accountList = accountService.findAll();
            model.addAttribute("accountListDTO", accountMapper.toListDTO(accountList));

            return "back/account_list";
        } catch (Exception ex) {
            return "redirect:" + redirectUrl;
        }
    }

    @GetMapping(value = {"/form"})
    public String create(Model model) {
        try {
            List<Account> accountList = accountService.findAll();
            AccountDTO accountDTO = new AccountDTO();
            accountDTO.setStatus(true);
            model.addAttribute("accountDTO", accountDTO);
            model.addAttribute("accountList", accountMapper.toListDTO(accountList));
            return "back/account_form";
        } catch (Exception ex) {
            return "redirect:" + redirectUrl;
        }
    }

    @GetMapping(value = {"/form/{id}"})
    public String edit(Model model, @PathVariable long id, @RequestParam(required = false) String action,
                       @RequestParam(required = false) String status) {
        try {
            Account account = accountService.findById(id);
            if (account == null) {
                return "redirect:" + redirectUrl;
            }

            model.addAttribute("accountDTO", accountMapper.toDTO(account));
            model.addAttribute("errorList", new HashMap<>());

            if (action == null) {
                model.addAttribute("messageDTO", null);
            } else {
                model.addAttribute("messageDTO", new MessageDTO(action, status,
                        status.equalsIgnoreCase("success") ? "Cập nhật dữ liệu thành công!" : "Vui lòng kiểm tra lại thông tin!"));
            }

            return "back/account_form";
        } catch (Exception ex) {
            return "redirect:" + redirectUrl;
        }
    }

    @PostMapping(value = "/form")
    public String save(Model model, AccountDTO accountDTO, BindingResult bindingResult) {
        try {
            // verify value
            accountValidator.validate(accountDTO, bindingResult);

            if (bindingResult.hasErrors()) {
                model.addAttribute("errorList", ValidatorUtil.toErrors(bindingResult.getFieldErrors()));
                model.addAttribute("messageDTO", new MessageDTO("save", "warning",
                        "Vui lòng kiểm tra lại thông tin!"));
                return "back/account_form";
            } else {
                // save
                Account account = accountService.save(accountDTO);

                redirectUrl = "/back/account/form/" + account.getId() + "?action=save&status=success";
                return "redirect:" + redirectUrl;
            }
        } catch (Exception ex) {
            return "redirect:" + redirectUrl;
        }
    }

}