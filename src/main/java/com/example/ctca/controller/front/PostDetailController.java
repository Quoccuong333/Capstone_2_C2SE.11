package com.example.ctca.controller.front;

import com.example.ctca.model.entity.Post;
import com.example.ctca.model.mapper.AccountMapper;
import com.example.ctca.model.mapper.PostMapper;
import com.example.ctca.service.AccountService;
import com.example.ctca.service.CategoryService;
import com.example.ctca.service.PostService;
import com.example.ctca.utils.FormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/post/detail")
public class PostDetailController {

    @Autowired
    private PostService postService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private AccountMapper accountMapper;

    @GetMapping("/{id}")
    public String view(Model model, @PathVariable long id) {
        Post post = postService.findById(id);
        if (post == null) return "front/404_error";

        model.addAttribute("postDTO", postMapper.toDTO(post));
        model.addAttribute("ownerDTO", accountMapper.toDTO(accountService.findById(post.getOwner().getId())));

        return "front/post_detail";
    }

    @GetMapping(value = {"", "/"})
    public String view() {
        return "front/post_detail";
    }

}
