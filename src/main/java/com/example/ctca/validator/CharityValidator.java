package com.example.ctca.validator;

import com.example.ctca.model.dto.CharityDTO;
import com.example.ctca.model.dto.PostDTO;
import com.example.ctca.model.entity.Charity;
import com.example.ctca.model.entity.Post;
import com.example.ctca.service.CharityService;
import com.example.ctca.service.PostService;
import com.example.ctca.utils.DateUtil;
import com.example.ctca.utils.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Date;

@Component
public class CharityValidator implements Validator {

    @Autowired
    private CharityService charityService;

    @Override
    public boolean supports(Class<?> clazz) {
        return CharityDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        try {
            CharityDTO charityDTO = (CharityDTO) target;
            Charity charity = null;
            // verify title
            if (ValidatorUtil.isEmpty(charityDTO.getTitle())) {
                errors.rejectValue("title", "Vui lòng nhập tiêu đề!",
                        "Vui lòng nhập tiêu đề!");
            }

            if (ValidatorUtil.isEmpty(charityDTO.getDescription())) {
                errors.rejectValue("description", "Vui lòng nhập mô tả!",
                        "Vui lòng nhập mô tả!");
            }

            if (charityDTO.getStartDate() != null && !charityDTO.getStartDate().trim().isEmpty() &&
                    charityDTO.getEndDate() != null && !charityDTO.getEndDate().trim().isEmpty()) {
                Date dateStart = DateUtil.convertStringToDate(charityDTO.getStartDate(), "yyyy-MM-dd");
                Date dateEnd = DateUtil.convertStringToDate(charityDTO.getEndDate(), "yyyy-MM-dd");
                boolean isStartBeforeEnd = DateUtil.compareDate(dateStart, dateEnd);
                if (dateStart.compareTo(dateEnd) > 0) {
                    errors.rejectValue("startDate", "Thời gian bắt đầu không được lớn hơn thời gian kết thúc",
                            "Thời gian bắt đầu không được lớn hơn thời gian kết thúc");
                }
            }

        } catch (Exception e) {
            errors.rejectValue("msg", "Có lỗi xảy ra, vui lòng thử lại!",
                    "Có lỗi xảy ra, vui lòng thử lại!");
        }
    }
}
