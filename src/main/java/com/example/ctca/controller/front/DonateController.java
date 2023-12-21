package com.example.ctca.controller.front;

import com.example.ctca.config.custom.CustomUserDetails;
import com.example.ctca.model.dto.*;
import com.example.ctca.model.entity.Account;
import com.example.ctca.model.entity.Charity;
import com.example.ctca.model.entity.CharityDonation;
import com.example.ctca.model.mapper.AccountMapper;
import com.example.ctca.model.mapper.CharityMapper;
import com.example.ctca.model.mapper.impl.CharityDonationMapperImpl;
import com.example.ctca.service.*;
import com.example.ctca.utils.ValidatorUtil;
import com.example.ctca.validator.AccountValidator;
import com.example.ctca.validator.DonateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(value = {"/donate/form"})
public class DonateController {
    private static final String REDIRECT_URL = "/donate/form";

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private CharityService charityService;

    @Autowired
    private CharityMapper charityMapper;

    @Autowired
    private CharityDonationService charityDonationService;

    @Autowired
    private CharityDonationMapperImpl charityDonationMapper;

    @Autowired
    private AccountValidator accountValidator;

    @Autowired
    private ValidatorUtil validatorUtil;

    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    DonateValidator donateValidator;


    @GetMapping(value = {"/{charityId}"})
    public String view(Model model, Authentication authentication,@PathVariable long charityId) {
        CharityDonationDTO charityDonationDTO = new CharityDonationDTO();

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Account account = customUserDetails.getAccount();
        AccountDTO accountDTO = accountMapper.toDTO(account);
        charityDonationDTO.setAccount(accountDTO);
        charityDonationDTO.setAccountId(accountDTO.getId());

        Charity charity = charityService.findById(charityId);
        CharityDTO charityDTO = charityMapper.toDTO(charity);
        charityDonationDTO.setCharity(charityDTO);
        charityDonationDTO.setCharityId(charityDTO.getId());

        model.addAttribute("charityDonationDTO", charityDonationDTO);

        return "front/donate_form";
    }

    @PostMapping(value = "/save")
    public String save(Model model, CharityDonationDTO charityDonationDTO,Authentication authentication, BindingResult bindingResult) {
        try {
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            Account account = customUserDetails.getAccount();

            if (bindingResult.hasErrors()) {
                model.addAttribute("errorList", validatorUtil.toErrors(bindingResult.getFieldErrors()));
                model.addAttribute("messageDTO", new MessageDTO("save", "warning",
                        "Vui lòng kiểm tra lại thông tin!"));
                return "/front/donate_form";
            } else {
                // save
                CharityDonation charityDonation = charityDonationMapper.toEntity(charityDonationDTO);
                charityDonation.setAccount(account);
                charityDonation.setProgress("PENDING");
                if (charityDonationDTO.getAvatarMul() != null && !ObjectUtils.isEmpty(charityDonationDTO.getAvatarMul().getOriginalFilename())) {
                    FileDTO fileDTOBack = fileUploadService.uploadFile(charityDonationDTO.getAvatarMul(), "IMAGE");
                    charityDonation.setImage(fileDTOBack.getPath());
                }
                charityDonationService.save(charityDonation);
                String redirectUrl = "/profile" + "?action=save&status=success&message=02";

                return "redirect:" + redirectUrl;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return "redirect:" + REDIRECT_URL;
        }
    }

    @GetMapping(value = {"update/{donateId}"})
    public String viewDonate(Model model,@PathVariable long donateId) {
        CharityDonation charityDonation = charityDonationService.findById(donateId);
        CharityDonationDTO charityDonationDTO = charityDonationMapper.toDTO(charityDonation);

        Account account = accountService.findById(charityDonation.getAccount().getId());
        AccountDTO accountDTO = accountMapper.toDTO(account);
        charityDonationDTO.setAccount(accountDTO);
        charityDonationDTO.setAccountId(accountDTO.getId());

        model.addAttribute("charityDonationDTO", charityDonationDTO);

        return "front/donate_check";
    }

    @PostMapping(value = "/update")
    public String updateProgress(Model model, CharityDonationDTO charityDonationDTO, BindingResult bindingResult) {
        try {

            if (bindingResult.hasErrors()) {
                model.addAttribute("errorList", validatorUtil.toErrors(bindingResult.getFieldErrors()));
                model.addAttribute("messageDTO", new MessageDTO("save", "warning",
                        "Vui lòng kiểm tra lại thông tin!"));

                return "/front/donate_check";
            } else {
                // save
                CharityDonation charityDonation = charityDonationMapper.toEntity(charityDonationDTO);
                charityDonationService.save(charityDonation);

                String redirectUrl = "/charity/detail/"+ String.valueOf(charityDonation.getCharity().getId());

                return "redirect:" + redirectUrl;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return "redirect:" + REDIRECT_URL;
        }
    }
}
