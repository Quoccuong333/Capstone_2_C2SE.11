package com.example.ctca.controller.front;

import com.example.ctca.config.custom.CustomUserDetails;
import com.example.ctca.model.entity.Account;
import com.example.ctca.model.entity.Post;
import com.example.ctca.model.mapper.AccountMapper;
import com.example.ctca.model.mapper.CommentMapper;
import com.example.ctca.model.mapper.PostMapper;
import com.example.ctca.service.AccountService;
import com.example.ctca.service.CommentService;
import com.example.ctca.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
    private CommentService commentService;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private AccountMapper accountMapper;

    @GetMapping("/{id}")
    public String view(Model model, Authentication authentication,  @PathVariable long id) {

        Post post = postService.findById(id);
        if (post == null) return "front/404_error";

        Account account;
        if(authentication != null){
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            account = customUserDetails.getAccount();
            model.addAttribute("isOwner", account != null && account.getId() == post.getOwner().getId());
            if (account !=null){
                if (account.getId() == post.getOwner().getId()){
                    model.addAttribute("commentListDTO", commentMapper.toListDTO(commentService.findByPostId(id)));
                }else {
                    model.addAttribute("commentListDTO", commentMapper.toListDTO(commentService.findByAccountIdAndPostId(account.getId(),id)));
                }
            }
        }
        model.addAttribute("postDTO", postMapper.toDTO(post));
        model.addAttribute("ownerDTO", accountMapper.toDTO(accountService.findById(post.getOwner().getId())));

        return "front/post_detail";
    }

    @GetMapping(value = {"", "/"})
    public String view() {
        return "front/post_detail";
    }

}
