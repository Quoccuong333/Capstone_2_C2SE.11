package com.example.ctca.controller.back;

import com.example.ctca.model.mapper.CharityMapper;
import com.example.ctca.model.mapper.PostMapper;
import com.example.ctca.service.AccountService;
import com.example.ctca.service.CharityService;
import com.example.ctca.service.CharityVolunteerService;
import com.example.ctca.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/back")
public class DashboardController {

    @Autowired
    AccountService accountService;

    @Autowired
    CharityService charityService;

    @Autowired
    CharityMapper charityMapper;

    @Autowired
    PostService postService;

    @Autowired
    PostMapper postMapper;

    @Autowired
    CharityVolunteerService charityVolunteerService;

    @GetMapping(value = {"/dashboard", "/dashboard/"})
    public String showDashboard(Model model) {
        model.addAttribute("totalAccount", accountService.findByStatusIsTrue().size());
        model.addAttribute("totalPost", postService.findAll().size());
        model.addAttribute("totalCharity", charityService.findAll().size());
        model.addAttribute("totalVolunteer", charityVolunteerService.findByStatusIsTrue().size());

        model.addAttribute("postList", postMapper.toListDTO(postService.findByProgress("PENDING")));
        model.addAttribute("charityList", charityMapper.toListDTO(charityService.findByProgress("PENDING")));
        return "back/dashboard";
    }

}
