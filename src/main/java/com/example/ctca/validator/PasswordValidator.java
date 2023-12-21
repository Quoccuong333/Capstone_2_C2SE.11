package com.example.ctca.validator;
import com.example.ctca.model.dto.AccountDTO;
import com.example.ctca.model.entity.Account;
import com.example.ctca.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PasswordValidator implements Validator {

    @Autowired
    private AccountService accountService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public boolean supports(Class<?> clazz) {
        return AccountDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        try {
            AccountDTO accountDTO = (AccountDTO) target;
            Account account = accountService.findById(accountDTO.getId());
            // verify old password
            if (accountDTO.getOldPassword() == null || accountDTO.getOldPassword().trim().isEmpty()){
                errors.rejectValue("oldPassword", "Vui lòng Nhập Mật khẩu cũ!", "Vui lòng Nhập Mật khẩu cũ!");
            }else {
                if (!passwordEncoder.matches(accountDTO.getOldPassword(), account.getPassword())){
                    errors.rejectValue("oldPassword", "Mật khẩu cũ không đúng!", "Mật khẩu cũ không đúng!");
                }
            }

            // verify new password
            if (accountDTO.getVerifyNewPassword() == null || accountDTO.getVerifyNewPassword().trim().isEmpty()) {
                errors.rejectValue("verifyNewPassword", "Vui lòng nhập Mật khẩu mới!",
                        "Vui lòng nhập Mật khẩu mới!");
            }else {
                if (!accountDTO.getNewPassword().equalsIgnoreCase(accountDTO.getVerifyNewPassword())){
                    errors.rejectValue("verifyNewPassword", "Mật khẩu không trùng khớp!",
                            "Mật khẩu không trùng khớp!");
                }
            }

        } catch (Exception e) {
            errors.rejectValue("msg", "Có lỗi xảy ra, vui lòng thử lại!",
                    "Có lỗi xảy ra, vui lòng thử lại!");
        }
    }
}
