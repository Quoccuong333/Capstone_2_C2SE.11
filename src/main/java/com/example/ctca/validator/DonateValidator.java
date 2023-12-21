package com.example.ctca.validator;

import com.example.ctca.model.dto.CharityDonationDTO;
import com.example.ctca.utils.ValidatorUtil;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
public class DonateValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return CharityDonationDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        try {
            CharityDonationDTO charityDonationDTO = (CharityDonationDTO) target;
            // verify image
            if (ValidatorUtil.isEmpty(charityDonationDTO.getImage())) {
                errors.rejectValue("image", "Vui lòng nhập hình ảnh quyên góp!",
                        "Vui lòng nhập hình ảnh quyên góp!");
            }

        } catch (Exception e) {
            errors.rejectValue("msg", "Có lỗi xảy ra, vui lòng thử lại!",
                    "Có lỗi xảy ra, vui lòng thử lại!");
        }
    }
}
