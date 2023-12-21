package com.example.ctca.validator;

import com.example.ctca.model.dto.AccountDTO;
import com.example.ctca.model.entity.Account;
import com.example.ctca.service.AccountService;
import com.example.ctca.utils.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class RegisterValidator implements Validator {

    @Autowired
    private AccountService accountService;

    @Override
    public boolean supports(Class<?> clazz) {
        return AccountDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        try {
            AccountDTO accountDTO = (AccountDTO) target;

            // verify fullName
            if (ValidatorUtil.isEmpty(accountDTO.getFullName())) {
                errors.rejectValue("fullName", "Vui lòng nhập Họ và Tên!",
                        "Vui lòng nhập Họ và Tên!");
            }

            //verify email
            if (ValidatorUtil.isEmpty(accountDTO.getEmail())) {
                errors.rejectValue("email", "Vui lòng nhập Email", "Vui lòng nhập Email");
            } else {
                if (!ValidatorUtil.checkEmail(accountDTO.getEmail())) {
                    errors.rejectValue("email", "Vui lòng nhập Email đúng định dạng", "Vui lòng nhập Email đúng định dạng");
                } else {
                    Account account = accountService.findByEmail(accountDTO.getEmail());
                    if (account != null && account.getEmail().equals(accountDTO.getEmail())) {
                        errors.rejectValue("email", "Email đã được đăng ký!",
                                "Email đã được đăng ký!");
                    }
                }
            }

            // verify password
            if (ValidatorUtil.isEmpty(accountDTO.getPassword())) {
                errors.rejectValue("password", "Vui lòng nhập Mật Khẩu!",
                        "Vui lòng nhập Mật Khẩu!");
            } else {
                if (accountDTO.getPassword().length() < 6) {
                    errors.rejectValue("password", "Vui lòng nhập Mật Khẩu lớn hơn 6 ký tự!",
                            "Vui lòng nhập Mật Khẩu lớn hơn 6 ký tự!");
                }
            }

            // verify confirmPassword
            if (ValidatorUtil.isEmpty(accountDTO.getConfirmPassword())) {
                errors.rejectValue("confirmPassword", "Vui lòng nhập Xác Nhận Mật Khẩu!",
                        "Vui lòng nhập Xác Nhận Mật Khẩu!");
            } else {
                if (accountDTO.getConfirmPassword().length() < 6) {
                    errors.rejectValue("confirmPassword", "Vui lòng nhập Xác Nhận Mật Khẩu lớn hơn 6 ký tự!",
                            "Vui lòng nhập Xác Nhận Mật Khẩu lớn hơn 6 ký tự!");
                } else if (!accountDTO.getConfirmPassword().equals(accountDTO.getPassword())) {
                    errors.rejectValue("confirmPassword", "Xác Nhận Mật Khẩu không trùng khớp với Mật Khẩu!",
                            "Xác Nhận Mật Khẩu không trùng khớp với Mật Khẩu!");
                }
            }

            // verify phone
            if (ValidatorUtil.isEmpty(accountDTO.getPhone())) {
                errors.rejectValue("phone", "Vui lòng nhập Số Điện Thoại!",
                        "Vui lòng nhập Số Điện Thoại!");
            } else {
                if (!ValidatorUtil.checkPhone(accountDTO.getPhone())) {
                    errors.rejectValue("phone", "Vui lòng nhập đúng định dạng", "Vui lòng nhập đúng định dạng");
                }
                else {
                    Account account = accountService.findByPhone(accountDTO.getPhone());
                    if (account != null && account.getPhone().equals(accountDTO.getPhone())) {
                        errors.rejectValue("phone", "Số Điện Thoại đã được đăng ký!",
                                "Số Điện Thoại đã được đăng ký!");
                    }
                }
            }
        } catch(Exception e) {
            errors.rejectValue("msg", "Có lỗi xảy ra, vui lòng thử lại!",
                    "Có lỗi xảy ra, vui lòng thử lại!");
        }

    }

}
