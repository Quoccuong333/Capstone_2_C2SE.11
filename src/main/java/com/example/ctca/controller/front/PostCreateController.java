package com.example.ctca.controller.front;

import com.example.ctca.config.custom.CustomUserDetails;
import com.example.ctca.model.dto.FileDTO;
import com.example.ctca.model.dto.PostDTO;
import com.example.ctca.model.entity.Account;
import com.example.ctca.model.entity.Category;
import com.example.ctca.model.entity.Post;
import com.example.ctca.model.mapper.CategoryMapper;
import com.example.ctca.model.mapper.PostMapper;
import com.example.ctca.service.CategoryService;
import com.example.ctca.service.FileUploadService;
import com.example.ctca.service.PostService;
import com.example.ctca.validator.PostValidator;
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

import java.util.List;

@Controller
@RequestMapping(value = {"/post/form"})
public class PostCreateController {

    private static final String REDIRECT_URL = "/post/form";

    @Autowired
    private PostService postService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private PostValidator postValidator;

    @Autowired
    private FileUploadService fileUploadService;

    @GetMapping(value = {""})
    public String view(Model model) {
        PostDTO postDTO = new PostDTO();
        List<Category> categoryList = categoryService.findAll();

        model.addAttribute("postDTO", postDTO);
        model.addAttribute("categoryListDTO", categoryMapper.toListDTO(categoryList));

        return "front/post_create";
    }

    @GetMapping("/{postId}")
    public String edit(Model model, Authentication authentication, @PathVariable long postId) {
        Post post = postService.findById(postId);
        List<Category> categoryList = categoryService.findAll();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Account account = customUserDetails.getAccount();
        if (account != null && account.getId() != post.getOwner().getId()) {
            return "error";
        }
        model.addAttribute("postDTO", postMapper.toDTO(post));
        model.addAttribute("categoryListDTO", categoryMapper.toListDTO(categoryList));

        return "front/post_create";
    }

    @PostMapping(value = "")
    public String save(Model model, Authentication authentication, PostDTO postDTO, BindingResult bindingResult) {
        try {
            if (authentication == null) {
                return "redirect:/login";
            }

            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            Account account = customUserDetails.getAccount();

            postDTO.setPrice(postDTO.getPrice().replace(",", ""));

            // validator
            postValidator.validate(postDTO, bindingResult);

            if (bindingResult.hasErrors()) {
                model.addAttribute("categoryListDTO", categoryMapper.toListDTO(categoryService.findAll()));
                model.addAttribute("postDTO", postDTO);
                return "front/post_create";
            }

            Post post = postMapper.toEntity(postDTO);
            post.setOwner(account);
            post.setStatus(true);
            post.setProgress("PENDING");
            if (postDTO.getAvatarMul() != null && !ObjectUtils.isEmpty(postDTO.getAvatarMul().getOriginalFilename())) {
                FileDTO fileDTOBack = fileUploadService.uploadFile(postDTO.getAvatarMul(), "IMAGE");
                post.setImage(fileDTOBack.getPath());
            }
            postService.save(post);

            String redirectUrl = "/profile" + "?action=save&status=success";
            return "redirect:" + redirectUrl;
        } catch (Exception ex) {
            return "redirect:" + REDIRECT_URL;
        }
    }

    @GetMapping(value = "/cancel/{postId}")
    public String cancel(Model model, Authentication authentication, @PathVariable long postId) {
        try {
            if (authentication == null) {
                return "redirect:/login";
            }
            Post post = postService.findById(postId);
            post.setProgress("CANCELED");
            postService.save(post);

            String redirectUrl = "/profile" + "?action=save&status=success&message=03";
            return "redirect:" + redirectUrl;
        } catch (Exception ex) {
            return "redirect:" + REDIRECT_URL;
        }
    }

    @GetMapping(value = "/complete/{postId}")
    public String complete(Model model, Authentication authentication, @PathVariable long postId) {
        try {
            if (authentication == null) {
                return "redirect:/login";
            }
            Post post = postService.findById(postId);
            post.setProgress("COMPLETED");
            postService.save(post);

            String redirectUrl = "/profile" + "?action=save&status=success&message=03";
            return "redirect:" + redirectUrl;
        } catch (Exception ex) {
            return "redirect:" + REDIRECT_URL;
        }
    }

}
