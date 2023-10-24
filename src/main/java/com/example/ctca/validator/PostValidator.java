package com.example.ctca.validator;

import com.example.ctca.model.dto.PostDTO;
import com.example.ctca.model.entity.Post;
import com.example.ctca.service.PostService;
import com.example.ctca.utils.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PostValidator implements Validator {

    @Autowired
    private PostService postService;


    @Override
    public boolean supports(Class<?> clazz) {
        return PostDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        try {
            PostDTO postDTO = (PostDTO) target;
            Post post = null;
            if (postDTO != null) {
                // verify title
                if (ValidatorUtil.isEmpty(postDTO.getName())) {
                    errors.rejectValue("name", "Vui lòng nhập tiêu đề!",
                            "Vui lòng nhập tiêu đề!");
                }

                // verify email
                if (ValidatorUtil.isEmpty(postDTO.getDescription())) {
                    errors.rejectValue("description", "Vui lòng nhập mô tả!",
                            "Vui lòng nhập mô tả!");
                }

                // verify tenDangNhap
                if (ValidatorUtil.isEmpty(postDTO.getType())) {
                    errors.rejectValue("type", "Vui lòng nhập loại bài đăng!",
                            "Vui lòng nhập loại bài đăng!");
                }


            }


        } catch (Exception e) {
            errors.rejectValue("msg", "Có lỗi xảy ra, vui lòng thử lại!",
                    "Có lỗi xảy ra, vui lòng thử lại!");
        }
    }
}
