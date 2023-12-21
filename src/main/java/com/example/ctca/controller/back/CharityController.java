package com.example.ctca.controller.back;

import com.example.ctca.config.custom.CustomUserDetails;
import com.example.ctca.model.dto.CharityDTO;
import com.example.ctca.model.dto.FileDTO;
import com.example.ctca.model.dto.MessageDTO;
import com.example.ctca.model.entity.Account;
import com.example.ctca.model.entity.Charity;
import com.example.ctca.model.mapper.CharityMapper;
import com.example.ctca.service.CharityService;
import com.example.ctca.service.FileUploadService;
import com.example.ctca.utils.ValidatorUtil;
import com.example.ctca.validator.CharityValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/back/charity")
public class CharityController {

    private static final String REDIRECT_URL = "/back/charity";

    @Autowired
    private CharityService charityService;

    @Autowired
    private CharityMapper charityMapper;

    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    private CharityValidator charityValidator;

    @GetMapping(value = {"/", ""})
    public String list(Model model) {
        try {
            List<Charity> charityList = charityService.findAll();
            model.addAttribute("charityListDTO", charityMapper.toListDTO(charityList));

            return "back/charity_list";
        } catch (Exception ex) {
            return "redirect:" + REDIRECT_URL;
        }
    }

    @GetMapping(value = "/form")
    public String create(Model model) {
        try {
            CharityDTO charityDTO = new CharityDTO();
            charityDTO.setStatus(true);

            model.addAttribute("charityDTO", charityDTO);

            return "back/charity_form";
        } catch (Exception ex) {
            return "redirect:" + REDIRECT_URL;
        }
    }

    @GetMapping(value = "/form/{id}")
    public String edit(Model model, @PathVariable long id,
                       @RequestParam(required = false) String action,
                       @RequestParam(required = false) String status) {
        try {
            Charity charity = charityService.findById(id);

            if (charity == null)
                return "redirect:" + REDIRECT_URL;

            model.addAttribute("charityDTO", charityMapper.toDTO(charity));
            model.addAttribute("errorList", new HashMap<>());

            if (action == null) {
                model.addAttribute("messageDTO", null);
            } else {
                model.addAttribute("messageDTO", new MessageDTO(action, status,
                        status.equalsIgnoreCase("success")
                                ? "Cập nhật dữ liệu thành công!"
                                : "Vui lòng kiểm tra lại thông tin!"));
            }

            return "back/charity_form";
        } catch (Exception ex) {
            return "redirect:" + REDIRECT_URL;
        }
    }

    @PostMapping(value = "/form")
    public String save(Model model, Authentication authentication, CharityDTO charityDTO, BindingResult bindingResult) {
        try {

            charityValidator.validate(charityDTO, bindingResult);

            if (bindingResult.hasErrors()) {
                model.addAttribute("errorList", ValidatorUtil.toErrors(bindingResult.getFieldErrors()));
                model.addAttribute("messageDTO", new MessageDTO("save", "warning",
                        "Vui lòng kiểm tra lại thông tin!"));

                return "back/charity_form";
            }
            Charity charity = charityMapper.toEntity(charityDTO);
            CustomUserDetails customUserDetail = (CustomUserDetails) authentication.getPrincipal();
            if (!ObjectUtils.isEmpty(customUserDetail) && ObjectUtils.isEmpty(charity.getOwner())) {
                Account account = customUserDetail.getAccount();
                charity.setOwner(account);
            }

            if (charityDTO.getAvatarMul() != null && !ObjectUtils.isEmpty(charityDTO.getAvatarMul().getOriginalFilename())) {
                FileDTO fileDTOBack = fileUploadService.uploadFile(charityDTO.getAvatarMul(), "IMAGE");
                charity.setImage(fileDTOBack.getPath());
            }
            charityService.save(charity);

            String redirectUrl = "/back/charity/form/" + charity.getId() + "?action=save&status=success";
            return "redirect:" + redirectUrl;
        } catch (Exception ex) {
            return "redirect:" + REDIRECT_URL;
        }
    }

}
