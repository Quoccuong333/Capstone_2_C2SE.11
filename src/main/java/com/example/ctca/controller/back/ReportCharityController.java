package com.example.ctca.controller.back;

import com.example.ctca.model.dto.CharityDTO;
import com.example.ctca.model.dto.CharityVolunteerDTO;
import com.example.ctca.model.dto.ReportDTO;
import com.example.ctca.model.mapper.CharityMapper;
import com.example.ctca.model.mapper.CharityVolunteerMapper;
import com.example.ctca.service.CharityService;
import com.example.ctca.service.CharityVolunteerService;
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
public class ReportCharityController {

    @Autowired
    CharityService charityService;

    @Autowired
    CharityMapper charityMapper;

    @Autowired
    DateUtil dateUtil;

    @Autowired
    ReportValidator reportValidator;

    @Autowired
    CharityVolunteerService charityVolunteerService;

    @Autowired
    CharityVolunteerMapper charityVolunteerMapper;


    @GetMapping(value = {"/report/charity"})
    public String showPost(Model model) {
        ReportDTO reportDTO = new ReportDTO();
        Date date = new Date();
        String dateStr = dateUtil.convertDateToString(date, "yyyy-MM-dd");
        reportDTO.setStartDate(dateStr);
        reportDTO.setEndDate(dateStr);

        setModel(model, reportDTO, "dashboard");

        return "back/report_charity";
    }

    @GetMapping(value = {"/report/charity/search"})
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

            return "back/report_charity";
        }

        setModel(model, reportDTO, "");
        return "back/report_charity";
    }

    private void setModel(Model model, ReportDTO reportDTO, String type) {
        List<CharityDTO> charityDTOList;
        if (type.equals("dashboard")) {
            charityDTOList = charityMapper.toListDTO(charityService.findAll());
        } else {
            charityDTOList = charityMapper.toListDTO(charityService.findReport(reportDTO));
        }

        int totalVolunteer = 0;
        int countPending =0;
        int countCompleted =0;
        String progressPending = "PENDING";
        String progressCompleted = "COMPLETED";
        for(CharityDTO charityDTO : charityDTOList) {
            List<CharityVolunteerDTO> charityVolunteerDTOList = charityVolunteerMapper.toListDTO(charityVolunteerService.findAllByCharity(charityDTO.getId()));
            totalVolunteer += charityVolunteerDTOList.size();

            if (charityDTO.getProgress().equals(progressPending)){
                countPending += 1;
            }

            if (charityDTO.getProgress().equals(progressCompleted)){
                countCompleted += 1;
            }
        }
        reportDTO.setTotalVolunteer(totalVolunteer);
        reportDTO.setTotalCharity(charityDTOList.size());
        reportDTO.setTotalPending(countPending);
        reportDTO.setTotalCompleted(countCompleted);

        model.addAttribute("charityDTOList", charityDTOList);
        model.addAttribute("reportDTO", reportDTO);
    }

}
