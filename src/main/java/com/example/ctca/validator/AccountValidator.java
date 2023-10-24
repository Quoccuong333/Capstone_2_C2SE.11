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
public class AccountValidator implements Validator {

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
            Account account = null;
            // verify title
            if (ValidatorUtil.isEmpty(accountDTO.getFullName())) {
                errors.rejectValue("fullName", "Vui lòng nhập họ tên!",
                        "Vui lòng nhập họ tên!");
            } else {
                if (accountDTO.getFullName().length()>255){
                    errors.rejectValue("fullName","Tên không được vượt quá 255 kí tự","Tên không được vượt quá 255 kí tự");
                }
            }

            // verify email
            if (ValidatorUtil.isEmpty(accountDTO.getEmail())) {
                errors.rejectValue("email", "Vui lòng nhập Địa chỉ Email!",
                        "Vui lòng nhập Địa chỉ Email!");
            } else {
                if (!ValidatorUtil.checkEmail(accountDTO.getEmail())) {
                    errors.rejectValue("email", "Email không đúng định dạng!",
                            "Email không đúng định dạng!");
                } else {
                    account = accountService.findByEmail(accountDTO.getEmail().trim());
                    if (account != null && account.getId() != accountDTO.getId()) {
                        errors.rejectValue("email", "Địa chỉ Email đã được đăng ký!",
                                "Địa chỉ Email đã được đăng ký!");
                    }
                }
            }

            // verify soDienThoai
            String phone = accountDTO.getPhone();
            if (ValidatorUtil.isEmpty(phone)) {
                errors.rejectValue("phone", "Vui lòng nhập Số Điện Thoại!",
                        "Vui lòng nhập Số Điện Thoại!");
            } else {
                if (!ValidatorUtil.checkPhone(phone)) {
                    errors.rejectValue("phone", "Vui lòng nhập đúng định dạng", "Vui lòng nhập đúng định dạng");
                } else {
                    account = accountService.findByPhone(phone);
                    if (account != null && account.getId() != accountDTO.getId()) {
                        errors.rejectValue("phone", "Số Điện Thoại đã được đăng ký!",
                                "Số Điện Thoại đã được đăng ký!");
                    }
                }
            }

        } catch (Exception e) {
            errors.rejectValue("msg", "Có lỗi xảy ra, vui lòng thử lại!",
                    "Có lỗi xảy ra, vui lòng thử lại!");
        }
    }
}
