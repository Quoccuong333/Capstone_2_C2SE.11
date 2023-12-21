package com.example.ctca.controller.front;

import com.example.ctca.config.custom.CustomUserDetails;
import com.example.ctca.model.dto.CharityDTO;
import com.example.ctca.model.dto.FileDTO;
import com.example.ctca.model.entity.Account;
import com.example.ctca.model.entity.Charity;
import com.example.ctca.model.mapper.CharityMapper;
import com.example.ctca.service.CharityService;
import com.example.ctca.service.FileUploadService;
import com.example.ctca.validator.CharityValidator;
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
@RequestMapping(value = {"charity/form"})
public class CharityCreateController {

    private static final String REDIRECT_URL = "/charity/form";

    @Autowired
    private CharityService charityService;

    @Autowired
    private CharityMapper charityMapper;

    @Autowired
    private CharityValidator charityValidator;

    @Autowired
    private FileUploadService fileUploadService;

    @GetMapping("")
    public String view(Model model) {
        model.addAttribute("charityDTO", new CharityDTO());
        return "front/charity_create";
    }

    @GetMapping("/{charityId}")
    public String edit(Model model, Authentication authentication, @PathVariable long charityId) {
        Charity charity = charityService.findById(charityId);
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Account account = customUserDetails.getAccount();
        if (account != null && account.getId() != charity.getOwner().getId()) {
            return "error";
        }
        model.addAttribute("charityDTO", charityMapper.toDTO(charity));
        return "front/charity_create";
    }

    @PostMapping(value = "")
    public String save(Model model, Authentication authentication, CharityDTO charityDTO, BindingResult bindingResult) {
        try {
            if (authentication == null) {
                return "redirect:/login";
            }
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            Account account = customUserDetails.getAccount();

            // validator
            charityValidator.validate(charityDTO, bindingResult);

            if (bindingResult.hasErrors()) {
                model.addAttribute("charityDTO", charityDTO);
                return "front/charity_create";
            }

            Charity charity = charityMapper.toEntity(charityDTO);
            charity.setOwner(account);
            charity.setStatus(true);
            charity.setProgress("PENDING");
            if (charityDTO.getAvatarMul() != null && !ObjectUtils.isEmpty(charityDTO.getAvatarMul().getOriginalFilename())) {
                FileDTO fileDTOBack = fileUploadService.uploadFile(charityDTO.getAvatarMul(), "IMAGE");
                charity.setImage(fileDTOBack.getPath());
            }
            charityService.save(charity);

            String redirectUrl = "/profile" + "?action=save&status=success";
            return "redirect:" + redirectUrl;
        } catch (Exception ex) {
            return "redirect:" + REDIRECT_URL;
        }
    }

    @GetMapping(value = "/cancel/{charityId}")
    public String cancel(Model model, Authentication authentication, @PathVariable long charityId) {
        try {
            if (authentication == null) {
                return "redirect:/login";
            }
            Charity charity = charityService.findById(charityId);
            charity.setProgress("CANCELED");
            charityService.save(charity);

            String redirectUrl = "/profile" + "?action=save&status=success&message=03";
            return "redirect:" + redirectUrl;
        } catch (Exception ex) {
            return "redirect:" + REDIRECT_URL;
        }
    }

    @GetMapping(value = "/complete/{charityId}")
    public String complete(Model model, Authentication authentication, @PathVariable long charityId) {
        try {
            if (authentication == null) {
                return "redirect:/login";
            }
            Charity charity = charityService.findById(charityId);
            charity.setProgress("COMPLETED");
            charityService.save(charity);

            String redirectUrl = "/profile" + "?action=save&status=success&message=03";
            return "redirect:" + redirectUrl;
        } catch (Exception ex) {
            return "redirect:" + REDIRECT_URL;
        }
    }

}
