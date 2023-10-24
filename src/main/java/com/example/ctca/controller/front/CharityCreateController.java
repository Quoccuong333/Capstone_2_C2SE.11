package com.example.ctca.controller.front;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = {"charity/create"})
public class CharityCreateController {

    @GetMapping(value = {"", "/"})
    public String view() {
        return "front/charity_create";
    }

}
