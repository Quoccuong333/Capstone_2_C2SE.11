package com.example.ctca.controller.front;

import com.example.ctca.config.custom.CustomUserDetails;
import com.example.ctca.model.dto.AccountDTO;
import com.example.ctca.model.dto.CommentDTO;
import com.example.ctca.model.dto.MessageDTO;
import com.example.ctca.model.dto.PostDTO;
import com.example.ctca.model.entity.Account;
import com.example.ctca.model.entity.Comment;
import com.example.ctca.model.entity.Post;
import com.example.ctca.model.mapper.AccountMapper;
import com.example.ctca.model.mapper.CommentMapper;
import com.example.ctca.model.mapper.PostMapper;
import com.example.ctca.service.CommentService;
import com.example.ctca.service.PostService;
import com.example.ctca.utils.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping(value = {"/comment/form"})
public class CommentController {
    private static final String REDIRECT_URL = "/comment/form";

    @Autowired
    private CommentService commentService;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private PostService postService;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    ValidatorUtil validatorUtil;

    @GetMapping(value = {"/{postId}"})
    public String view(Model model, Authentication authentication, @PathVariable long postId) {
        try {
            CommentDTO commentDTO = new CommentDTO();
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            Account account = customUserDetails.getAccount();
            AccountDTO accountDTO = accountMapper.toDTO(account);
            commentDTO.setAccountDTO(accountDTO);
            commentDTO.setAccountId(accountDTO.getId());

            Post post = postService.findById(postId);
            PostDTO postDTO = postMapper.toDTO(post);
            commentDTO.setPost(postDTO);
            commentDTO.setPostId(postDTO.getId());

            model.addAttribute("commentDTO", commentDTO);

            return "front/comment_form";
        } catch (Exception ex) {
            ex.printStackTrace();
            return "redirect:" + REDIRECT_URL;
        }
    }

    @PostMapping(value = "/save")
    public String save(Model model, CommentDTO commentDTO, Authentication authentication, BindingResult bindingResult, @RequestParam(required = false) String action,
                       @RequestParam(required = false) String status) {
        try {
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            Account account = customUserDetails.getAccount();
            if (account == null) {
                return "/front/comment_form";
            }

            if (commentDTO.getContent().length() > 254) {
                model.addAttribute("content", "Nội dung không được dài quá 255 ký tự!");
                return "/front/comment_form";
            }

            if (bindingResult.hasErrors()) {
                model.addAttribute("errorList", ValidatorUtil.toErrors(bindingResult.getFieldErrors()));
                model.addAttribute("messageDTO", new MessageDTO("save", "warning",
                        "Vui lòng kiểm tra lại thông tin!"));
                return "/front/comment_form";
            } else {
                // save
                Comment comment = commentMapper.toEntity(commentDTO);
                comment.setAccount(account);
                comment.setStatus(true);
                commentService.save(comment);

                String redirectUrl = "/post/detail/"+ String.valueOf(comment.getPost().getId());

                return "redirect:" + redirectUrl;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return "redirect:" + REDIRECT_URL;
        }
    }

}
