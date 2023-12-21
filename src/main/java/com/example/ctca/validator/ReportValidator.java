package com.example.ctca.validator;
import com.example.ctca.model.dto.ReportDTO;
import com.example.ctca.utils.DateUtil;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Date;

@Component
public class ReportValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return ReportDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ReportDTO reportDTO = (ReportDTO) target;

        if (reportDTO.getStartDate() != null && !reportDTO.getStartDate().trim().isEmpty() &&
                reportDTO.getEndDate() != null && !reportDTO.getEndDate().trim().isEmpty()) {
            Date dateStart = DateUtil.convertStringToDate(reportDTO.getStartDate(), "yyyy-MM-dd");
            Date dateEnd = DateUtil.convertStringToDate(reportDTO.getEndDate(), "yyyy-MM-dd");
            boolean isStartBeforeEnd = DateUtil.compareDate(dateStart, dateEnd);
            if (dateStart.compareTo(dateEnd) > 0) {
                errors.rejectValue("startDate", "Thời gian bắt đầu lớn hơn thời gian kết thúc",
                        "Thời gian bắt đầu lớn hơn thời gian kết thúc");
            }
        }

    }

}
