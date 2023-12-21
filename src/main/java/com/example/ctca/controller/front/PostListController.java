package com.example.ctca.controller.front;

import com.example.ctca.model.entity.Category;
import com.example.ctca.model.entity.Post;
import com.example.ctca.model.mapper.CategoryMapper;
import com.example.ctca.model.mapper.PostMapper;
import com.example.ctca.service.CategoryService;
import com.example.ctca.service.PostService;
import com.example.ctca.utils.ConstantUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/post")
public class PostListController {

    private static final String DEFAULT_PAGE = "1";

    private static final long DEFAULT_CATEGORY = 0;

    @Autowired
    private PostService postService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @GetMapping(value = {""})
    public String showPostAll(Model model, @RequestParam(value = "page", defaultValue = DEFAULT_PAGE,
                                       required = false) int pageNumber,
                              @RequestParam(value = "categoryId", defaultValue = "0", required = false) String categoryId, @RequestParam(required = false) String title) {
        long category = DEFAULT_CATEGORY;

        if (categoryId != null && categoryId.equalsIgnoreCase("-1")) {
            return "error";
        }

        if (categoryId != null || !categoryId.equalsIgnoreCase("")) {
            category = Long.parseLong(categoryId);
        }

        return listByPage(model, pageNumber, category, title);
    }

    private String listByPage(Model model, int pageNumber, long categoryId, String title) {
        List<Category> categoryList = categoryService.findAll();

        // pagination
        Pageable pageable = PageRequest.of(pageNumber - 1, ConstantUtil.PAGE_SIZE, Sort.by("name").ascending());
        Page<Post> postList;
        if (categoryId == DEFAULT_CATEGORY && StringUtils.isEmpty(title)) {
            postList = postService.findByCategoryAndStatusTrue(null, pageable);
        } else if (categoryId != DEFAULT_CATEGORY && StringUtils.isEmpty(title)) {
            Category category = categoryService.findById(categoryId);
            postList = postService.findByCategoryAndStatusTrue(category, pageable);

        } else if (categoryId == DEFAULT_CATEGORY && !StringUtils.isEmpty(title)) {
            postList = postService.findByCategoryAndStatusTrueAndNameContains(null, pageable, title);
        } else {
            Category category = categoryService.findById(categoryId);
            postList = postService.findByCategoryAndStatusTrueAndNameContains(category, pageable, title);
        }

        List<Post> postListByPage = new ArrayList<>(postList.getContent());

        model.addAttribute("postList", postMapper.toListDTO(postListByPage));
        model.addAttribute("categoryList", categoryMapper.toListDTO(categoryList));
        model.addAttribute("totalPage", postList.getTotalPages());
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("categoryId", categoryId);

        return "front/post_list";
    }

}
