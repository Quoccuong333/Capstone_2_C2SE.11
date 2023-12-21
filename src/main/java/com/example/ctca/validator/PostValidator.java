package com.example.ctca.validator;

import com.example.ctca.model.dto.PostDTO;
import com.example.ctca.model.entity.Post;
import com.example.ctca.utils.ValidatorUtil;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PostValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return PostDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        try {
            PostDTO postDTO = (PostDTO) target;
            Post post = null;
            // verify name
            if (ValidatorUtil.isEmpty(postDTO.getName())) {
                errors.rejectValue("name", "Vui lòng nhập tiêu đề!",
                        "Vui lòng nhập tiêu đề!");
            }

            // verify description
            if (ValidatorUtil.isEmpty(postDTO.getDescription())) {
                errors.rejectValue("description", "Vui lòng nhập mô tả!",
                        "Vui lòng nhập mô tả!");
            }

            // verify price
            if (ValidatorUtil.isEmpty(postDTO.getPrice())) {
                errors.rejectValue("price", "Vui lòng nhập giá bán!",
                        "Vui lòng nhập giá bán!");
            } else {
                if (!ValidatorUtil.isNumeric(postDTO.getPrice())) {
                    errors.rejectValue("price", "Giá bán không đúng đinh dạng!",
                            "Giá bán không đúng đinh dạng!");
                }
            }

            // verify type
            if (ValidatorUtil.isEmpty(postDTO.getType()) || postDTO.getType().equals("0")) {
                errors.rejectValue("type", "Vui lòng nhập loại bài đăng!",
                        "Vui lòng nhập loại bài đăng!");
            }

            // verify type
            if (postDTO.getCategoryId() == 0) {
                errors.rejectValue("categoryId", "Vui lòng chọn danh mục!",
                        "Vui lòng chọn danh mục!");
            }
        } catch (Exception e) {
        }
    }
}
