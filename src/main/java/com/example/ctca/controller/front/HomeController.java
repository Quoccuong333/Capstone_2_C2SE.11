package com.example.ctca.controller.front;

import com.example.ctca.model.entity.Category;
import com.example.ctca.model.entity.Charity;
import com.example.ctca.model.entity.Post;
import com.example.ctca.model.mapper.CategoryMapper;
import com.example.ctca.model.mapper.CharityMapper;
import com.example.ctca.model.mapper.PostMapper;
import com.example.ctca.service.CategoryService;
import com.example.ctca.service.CharityService;
import com.example.ctca.service.PostService;
import com.example.ctca.utils.ConstantUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = {"", "/home"})
public class HomeController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private CharityService charityService;

    @Autowired
    private CharityMapper charityMapper;

    @Autowired
    private PostService postService;

    @Autowired
    private PostMapper postMapper;

    @GetMapping(value = {"", "/"})
    public String view(Model model) {
        // danh sách danh mục
        List<Category> categoryList = categoryService.findAll();
        model.addAttribute("categoryList", categoryMapper.toListDTO(categoryList));

        // danh sách từ thiện
        List<Charity> charityList = charityService.findByRandom();
        model.addAttribute("charityList", charityMapper.toListDTO(charityList));

        // danh sách mua bán
        Pageable pageable = PageRequest.of(0, 8, Sort.by("createdOn").descending());
        Page<Post> postList = postService.findByCategoryAndStatusTrue(null, pageable);
        List<Post> postListByPage = new ArrayList<>(postList.getContent());
        model.addAttribute("postList", postMapper.toListDTO(postListByPage));

        return "front/home";
    }

}
