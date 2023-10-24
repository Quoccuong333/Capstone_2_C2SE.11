package com.example.ctca.controller.front;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = {"profile"})
public class MyProfileController {

    @GetMapping(value = {"", "/"})
    public String view() {
        return "front/my_profile";
    }

}
