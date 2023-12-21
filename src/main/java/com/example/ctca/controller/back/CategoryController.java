package com.example.ctca.controller.back;

import com.example.ctca.model.dto.CategoryDTO;
import com.example.ctca.model.dto.MessageDTO;
import com.example.ctca.model.entity.Category;
import com.example.ctca.model.mapper.CategoryMapper;
import com.example.ctca.service.CategoryService;
import com.example.ctca.service.FileUploadService;
import com.example.ctca.utils.ValidatorUtil;
import com.example.ctca.validator.CategoryValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/back/category")
public class CategoryController {

    private static final String REDIRECT_URL = "/back/category";

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    private CategoryValidator categoryValidator;

    @GetMapping(value = {"/", ""})
    public String list(Model model) {
        try {
            List<Category> categoryList = categoryService.findAll();
            model.addAttribute("categoryList", categoryMapper.toListDTO(categoryList));

            return "back/category_list";
        } catch (Exception ex) {
            return "redirect:" + REDIRECT_URL;
        }
    }

    @GetMapping(value = "/form")
    public String create(Model model) {
        try {
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setStatus(true);

            model.addAttribute("categoryDTO", categoryDTO);

            return "back/category_form";
        } catch (Exception ex) {
            return "redirect:" + REDIRECT_URL;
        }
    }

    @GetMapping(value = "/form/{id}")
    public String edit(Model model, @PathVariable long id,
                       @RequestParam(required = false) String action,
                       @RequestParam(required = false) String status) {
        try {
            Category category = categoryService.findById(id);

            if (category == null)
                return "redirect:" + REDIRECT_URL;

            model.addAttribute("categoryDTO", categoryMapper.toDTO(category));
            model.addAttribute("errorList", new HashMap<>());

            if (action == null) {
                model.addAttribute("messageDTO", null);
            } else {
                model.addAttribute("messageDTO", new MessageDTO(action, status,
                        status.equalsIgnoreCase("success")
                                ? "Cập nhật dữ liệu thành công!"
                                : "Vui lòng kiểm tra lại thông tin!"));
            }

            return "back/category_form";
        } catch (Exception ex) {
            return "redirect:" + REDIRECT_URL;
        }
    }

    @PostMapping(value = "/form")
    public String save(Model model, Authentication authentication, CategoryDTO categoryDTO, BindingResult bindingResult) {
        try {

            categoryValidator.validate(categoryDTO, bindingResult);

            if (bindingResult.hasErrors()) {
                model.addAttribute("errorList", ValidatorUtil.toErrors(bindingResult.getFieldErrors()));
                model.addAttribute("messageDTO", new MessageDTO("save", "warning",
                        "Vui lòng kiểm tra lại thông tin!"));

                return "back/category_form";
            }
            Category category = categoryService.save(categoryMapper.toEntity(categoryDTO));

            String redirectUrl = "/back/category/form/" + category.getId() + "?action=save&status=success";
            return "redirect:" + redirectUrl;
        } catch (Exception ex) {
            return "redirect:" + REDIRECT_URL;
        }
    }

}
