package com.example.ctca.controller.front;

import com.example.ctca.config.custom.CustomUserDetails;
import com.example.ctca.model.dto.MessageDTO;
import com.example.ctca.model.entity.Account;
import com.example.ctca.model.entity.Charity;
import com.example.ctca.model.entity.Post;
import com.example.ctca.model.mapper.CharityMapper;
import com.example.ctca.model.mapper.PostMapper;
import com.example.ctca.service.CharityService;
import com.example.ctca.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping(value = {"profile"})
public class ProfileController {

    @Autowired
    private PostService postService;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private CharityService charityService;

    @Autowired
    private CharityMapper charityMapper;

    @GetMapping(value = {""})
    public String view(Model model, Authentication authentication,
                       @RequestParam(required = false) String action,
                       @RequestParam(required = false) String status, @RequestParam(required = false) String message) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Account account = customUserDetails.getAccount();

        // danh sách bài đăng
        List<Post> postListByPage = postService.findByOwner(account.getId());
        model.addAttribute("postListDTO", postMapper.toListDTO(postListByPage));
        model.addAttribute("totalPost", postListByPage.size());

        // danh sách từ thiện
        List<Charity> charityListByPage = charityService.findByOwner(account.getId());
        model.addAttribute("charityListDTO", charityMapper.toListDTO(charityListByPage));
        model.addAttribute("totalCharity", charityListByPage.size());

        MessageDTO messageDTO = (MessageDTO) model.getAttribute("messageDTO");
        if (action == null) {
            model.addAttribute("messageDTO", null);
        } else {
            if (StringUtils.isEmpty(message)) {
                model.addAttribute("messageDTO", new MessageDTO(action, status,
                        "Đăng bài thành công! Vui lòng chờ quản lý duyệt!"));
            } else {
                switch (message) {
                    case "01" -> message = "Bạn đã đăng ký tình nguyện và đang chờ duyệt!";
                    case "02"-> message = "Bạn đã đăng ký quyên góp và đang chờ duyệt!";
                    case "03" -> message = "Cập nhật thông tin thành công!";
                }
                model.addAttribute("messageDTO", new MessageDTO(action, status, message));
            }
        }

        return "front/profile";
    }

}
