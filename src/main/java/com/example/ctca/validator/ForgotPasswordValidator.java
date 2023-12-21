package com.example.ctca.validator;

import com.example.ctca.model.dto.AccountDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ForgotPasswordValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return AccountDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AccountDTO accountDTO = (AccountDTO) target;

        // verify new password
        if(accountDTO.getNewPassword() == null || accountDTO.getNewPassword().trim().isEmpty()){
            errors.rejectValue("newPassword", "Vui lòng nhập mật khẩu mới!",
                    "Vui lòng nhập mật khẩu mới!");
        }else{
            if (accountDTO.getNewPassword().length() < 6) {
                errors.rejectValue("newPassword", "Mật khẩu cần ít nhất 8 ký tự!",
                        "Mật khẩu cần ít nhất 6 ký tự!");
            }
        }

        // verify new password again
        if (accountDTO.getConfirmPassword() == null || accountDTO.getConfirmPassword().trim().isEmpty()) {
            errors.rejectValue("confirmPassword", "Vui lòng nhập lại mật khẩu mới!",
                    "Vui lòng nhập lại mật khẩu mới!");
        } else {
            if (!accountDTO.getNewPassword().equalsIgnoreCase(accountDTO.getConfirmPassword())) {
                errors.rejectValue("confirmPassword", "Mật khẩu không trùng khớp!",
                        "Mật khẩu không trùng khớp!");
            }
        }
    }
}
