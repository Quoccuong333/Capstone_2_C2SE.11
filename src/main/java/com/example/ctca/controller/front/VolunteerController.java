package com.example.ctca.controller.front;

import com.example.ctca.config.custom.CustomUserDetails;
import com.example.ctca.model.dto.AccountDTO;
import com.example.ctca.model.dto.CharityDTO;
import com.example.ctca.model.dto.CharityVolunteerDTO;
import com.example.ctca.model.dto.MessageDTO;
import com.example.ctca.model.entity.*;
import com.example.ctca.model.mapper.AccountMapper;
import com.example.ctca.model.mapper.CharityMapper;
import com.example.ctca.model.mapper.impl.CharityVolunteerMapperImpl;
import com.example.ctca.service.AccountService;
import com.example.ctca.service.CharityService;
import com.example.ctca.service.CharityVolunteerService;
import com.example.ctca.utils.ValidatorUtil;
import com.example.ctca.validator.AccountValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping(value = {"/volunteer/form"})
public class VolunteerController {
    private static final String REDIRECT_URL = "/volunteer/form";

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private CharityService charityService;

    @Autowired
    private CharityMapper charityMapper;

    @Autowired
    private CharityVolunteerService charityVolunteerService;

    @Autowired
    private CharityVolunteerMapperImpl charityVolunteerMapper;

    @Autowired
    private AccountValidator accountValidator;

    @Autowired
    private ValidatorUtil validatorUtil;


    @GetMapping(value = {"/{charityId}"})
    public String view(Model model, Authentication authentication, @PathVariable long charityId, @RequestParam(required = false) String action,
                       @RequestParam(required = false) String status) {
        CharityVolunteerDTO charityVolunteerDTO = new CharityVolunteerDTO();

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Account account = customUserDetails.getAccount();
        AccountDTO accountDTO = accountMapper.toDTO(account);
        charityVolunteerDTO.setAccount(accountDTO);
        charityVolunteerDTO.setAccountId(accountDTO.getId());
        boolean checkRegister = charityVolunteerService.checkRegisterVolunteer(charityId, accountDTO.getId());
        if (checkRegister) {
            Charity charity = charityService.findById(charityId);
            CharityDTO charityDTO = charityMapper.toDTO(charity);
            charityVolunteerDTO.setCharity(charityDTO);
            charityVolunteerDTO.setCharityId(charityDTO.getId());
            model.addAttribute("charityVolunteerDTO", charityVolunteerDTO);

            return "front/volunteer_form";
        }

        String redirectUrl = "/profile" + "?action=save&status=success&message=01";
        return "redirect:" + redirectUrl;

    }

    @PostMapping(value = "/save")
    public String save(Model model, CharityVolunteerDTO charityVolunteerDTO, Authentication authentication, BindingResult bindingResult) {
        try {
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            Account account = customUserDetails.getAccount();


            if (bindingResult.hasErrors()) {
                model.addAttribute("errorList", validatorUtil.toErrors(bindingResult.getFieldErrors()));
                model.addAttribute("messageDTO", new MessageDTO("save", "warning",
                        "Vui lòng kiểm tra lại thông tin!"));

                return "/front/volunteer_form";
            } else {
                // save
                CharityVolunteer charityVolunteer = charityVolunteerMapper.toEntity(charityVolunteerDTO);
                charityVolunteer.setAccount(account);
                charityVolunteer.setProgress("PENDING");
                charityVolunteerService.save(charityVolunteer);
                String redirectUrl = "/profile" + "?action=save&status=success&message=01";

                return "redirect:" + redirectUrl;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return "redirect:" + REDIRECT_URL;
        }
    }

    @GetMapping(value = {"/check/{volunteerId}"})
    public String viewVolunteer(Model model, Authentication authentication, @PathVariable long volunteerId) {

        CharityVolunteer charityVolunteer = charityVolunteerService.findById(volunteerId);
        CharityVolunteerDTO charityVolunteerDTO = charityVolunteerMapper.toDTO(charityVolunteer);
        Account account = accountService.findById(charityVolunteer.getAccount().getId());
        AccountDTO accountDTO = accountMapper.toDTO(account);
        charityVolunteerDTO.setAccount(accountDTO);

        model.addAttribute("charityVolunteerDTO", charityVolunteerDTO);

        return "front/volunteer_check";
    }

    @PostMapping(value = "/check")
    public String checkProgress(Model model, CharityVolunteerDTO charityVolunteerDTO, BindingResult bindingResult) {
        try {

            if (bindingResult.hasErrors()) {
                model.addAttribute("errorList", validatorUtil.toErrors(bindingResult.getFieldErrors()));
                model.addAttribute("messageDTO", new MessageDTO("save", "warning",
                        "Vui lòng kiểm tra lại thông tin!"));

                return "/front/volunteer_check";
            } else {
                // save
                CharityVolunteer charityVolunteer = charityVolunteerMapper.toEntity(charityVolunteerDTO);
                charityVolunteerService.save(charityVolunteer);

                String redirectUrl = "/charity/detail/"+ String.valueOf(charityVolunteer.getCharity().getId());

                return "redirect:" + redirectUrl;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return "redirect:" + REDIRECT_URL;
        }
    }
}
