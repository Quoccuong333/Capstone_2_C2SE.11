package com.example.ctca.controller.front;

import com.example.ctca.model.entity.Charity;
import com.example.ctca.model.mapper.CharityMapper;
import com.example.ctca.service.CharityService;
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
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/charity")
public class CharityListController {

    private static final String DEFAULT_PAGE = "1";

    @Autowired
    private CharityService charityService;

    @Autowired
    private CharityMapper charityMapper;

    @GetMapping(value = {""})
    public String showCharityAll(Model model, @RequestParam(value = "page", defaultValue = DEFAULT_PAGE,
            required = false) int pageNumber) {
        // pagination
        Pageable pageable = PageRequest.of(pageNumber - 1, ConstantUtil.PAGE_SIZE, Sort.by("title").ascending());
        Page<Charity> charityList = charityService.findByStatusTrue(pageable);

        List<Charity> charityListByPage = new ArrayList<>(charityList.getContent());

        model.addAttribute("charityList", charityMapper.toListDTO(charityListByPage));
        model.addAttribute("totalPage", charityList.getTotalPages());
        model.addAttribute("currentPage", pageNumber);

        return "front/charity_list";
    }
}
