package com.example.ctca.controller.back;

import com.example.ctca.model.dto.PostDTO;
import com.example.ctca.model.dto.ReportDTO;
import com.example.ctca.model.mapper.PostMapper;
import com.example.ctca.service.AccountService;
import com.example.ctca.service.PostService;
import com.example.ctca.utils.DateUtil;
import com.example.ctca.utils.ValidatorUtil;
import com.example.ctca.validator.ReportValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/back")
public class ReportPostController {

    @Autowired
    AccountService accountService;

    @Autowired
    PostService postService;

    @Autowired
    PostMapper postMapper;

    @Autowired
    DateUtil dateUtil;

    @Autowired
    ReportValidator reportValidator;

    @Autowired
    ValidatorUtil validatorUtil;

    @GetMapping(value = {"/report/post"})
    public String showPost(Model model) {
        ReportDTO reportDTO = new ReportDTO();
        Date date = new Date();
        String dateStr = dateUtil.convertDateToString(date, "yyyy-MM-dd");
        reportDTO.setStartDate(dateStr);
        reportDTO.setEndDate(dateStr);

        setModel(model, reportDTO, "dashboard");

        return "back/report_post";
    }

    @GetMapping(value = {"/report/post/search"})
    public String search(Model model, ReportDTO reportDTO, BindingResult bindingResult) {
        reportValidator.validate(reportDTO, bindingResult);
        reportDTO.setSearch(true);

        if (bindingResult.hasErrors()) {
            Date date = new Date();
            String dateStr = DateUtil.convertDateToString(date, "yyyy-MM-dd");
            reportDTO.setStartDate(dateStr);
            reportDTO.setEndDate(dateStr);
            reportDTO.setError(true);
            setModel(model, reportDTO, "");
            model.addAttribute("errorList", ValidatorUtil.toErrors(bindingResult.getFieldErrors()));

            return "back/report_post";
        }

        setModel(model, reportDTO, "");
        return "back/report_post";
    }


    private void setModel(Model model, ReportDTO reportDTO, String type) {
        List<PostDTO> postDTOList;
        if (type.equals("dashboard")) {
            postDTOList = postMapper.toListDTO(postService.findAll());
        } else {
            postDTOList = postMapper.toListDTO(postService.findReport(reportDTO));
        }
        int countPending =0;
        int countCompleted =0;
        int countApproved =0;
        String progressPending = "PENDING";
        String progressCompleted = "COMPLETED";
        String progressApproved = "APPROVED";
        for(PostDTO postDTO : postDTOList) {
            if (postDTO.getProgress().equals(progressPending)){
                countPending += 1;
            }

            if (postDTO.getProgress().equals(progressCompleted)){
                countCompleted += 1;
            }

            if (postDTO.getProgress().equals(progressApproved)){
                countApproved += 1;
            }
        }
        reportDTO.setTotalPending(countPending);
        reportDTO.setTotalCompleted(countCompleted);
        reportDTO.setTotalApproved(countApproved);
        reportDTO.setTotalPost(postDTOList.size());
        model.addAttribute("postDTOList", postDTOList);
        model.addAttribute("reportDTO", reportDTO);
    }

}
