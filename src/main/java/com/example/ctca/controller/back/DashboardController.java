package com.example.ctca.controller.back;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/back")
public class DashboardController {

    @GetMapping(value = {"/dashboard", "/dashboard/"})
    public String showDashboard(Model model) {
        return "back/dashboard";
    }

}
