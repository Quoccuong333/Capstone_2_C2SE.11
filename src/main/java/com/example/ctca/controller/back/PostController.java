package com.example.ctca.controller.back;

import com.example.ctca.config.custom.CustomUserDetails;
import com.example.ctca.model.dto.FileDTO;
import com.example.ctca.model.dto.MessageDTO;
import com.example.ctca.model.dto.PostDTO;
import com.example.ctca.model.entity.Account;
import com.example.ctca.model.entity.Category;
import com.example.ctca.model.entity.Post;
import com.example.ctca.model.mapper.AccountMapper;
import com.example.ctca.model.mapper.CategoryMapper;
import com.example.ctca.model.mapper.PostMapper;
import com.example.ctca.service.AccountService;
import com.example.ctca.service.CategoryService;
import com.example.ctca.service.FileUploadService;
import com.example.ctca.service.PostService;
import com.example.ctca.utils.FormatUtils;
import com.example.ctca.validator.PostValidator;
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
@RequestMapping("/back/post")
public class PostController {

    private static final String REDIRECT_URL = "/back/post";

    @Autowired
    private PostService postService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    private PostValidator postValidator;

    @GetMapping(value = {"/", ""})
    public String list(Model model) {
        try {
            List<Post> postList = postService.findAll();
            model.addAttribute("postList", postMapper.toListDTO(postList));

            return "back/post_list";
        } catch (Exception ex) {
            return "redirect:" + REDIRECT_URL;
        }
    }

    @GetMapping(value = "/form")
    public String create(Model model) {
        try {
            PostDTO postDTO = new PostDTO();
            postDTO.setStatus(true);
            List<Category> categoryList = categoryService.findAll();
            List<Account> accountList =  accountService.findAll();

            model.addAttribute("postDTO", postDTO);
            model.addAttribute("categoryList", categoryMapper.toListDTO(categoryList));
            model.addAttribute("accountList", accountMapper.toListDTO(accountList));

            return "back/post_form";
        } catch (Exception ex) {
            return "redirect:" + REDIRECT_URL;
        }
    }

    @GetMapping(value = "/form/{id}")
    public String edit(Model model, @PathVariable long id,
                       @RequestParam(required = false) String action,
                       @RequestParam(required = false) String status) {
        try {

            Post post = postService.findById(id);

            if (post == null)
                return "redirect:" + REDIRECT_URL;
            List<Category> categoryList = categoryService.findAll();
            List<Account> accountList =  accountService.findAll();

            model.addAttribute("postDTO", postMapper.toDTO(post));
            model.addAttribute("categoryList", categoryMapper.toListDTO(categoryList));
            model.addAttribute("accountList", accountMapper.toListDTO(accountList));
            model.addAttribute("errorList", new HashMap<>());

            if (action == null) {
                model.addAttribute("messageDTO", null);
            } else {
                model.addAttribute("messageDTO", new MessageDTO(action, status,
                        status.equalsIgnoreCase("success")
                                ? "Cập nhật dữ liệu thành công!"
                                : "Vui lòng kiểm tra lại thông tin!"));
            }

            return "back/post_form";
        } catch (Exception ex) {
            return "redirect:" + REDIRECT_URL;
        }
    }

    @PostMapping(value = "/form")
    public String save(Model model, PostDTO postDTO, Authentication authentication, BindingResult bindingResult) {
        try {
            // format data
            postDTO.setPrice(FormatUtils.toEncodePrice(postDTO.getPrice()));

            postValidator.validate(postDTO, bindingResult);


            if (bindingResult.hasErrors()) {
                model.addAttribute("messageDTO", new MessageDTO("save", "warning",
                        "Vui lòng kiểm tra lại thông tin!"));
                model.addAttribute("categoryList", categoryService.findAll());
                model.addAttribute("accountList", accountService.findAll());
                return "back/post_form";
            }
            Post post = postMapper.toEntity(postDTO);

            if (postDTO.getAvatarMul() != null && !ObjectUtils.isEmpty(postDTO.getAvatarMul().getOriginalFilename())) {
                FileDTO fileDTOBack = fileUploadService.uploadFile(postDTO.getAvatarMul(), "IMAGE");
                post.setImage(fileDTOBack.getPath());
            }

            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            if (!ObjectUtils.isEmpty(customUserDetails) && ObjectUtils.isEmpty(post.getOwner())) {
                Account account = customUserDetails.getAccount();
                post.setOwner(accountService.findById(account.getId()));
            }

            postService.save(post);

            String redirectUrl = "/back/post/form/" + post.getId() + "?action=save&status=success";
            return "redirect:" + redirectUrl;
        } catch (Exception ex) {
            return "redirect:" + REDIRECT_URL;
        }
    }

}
