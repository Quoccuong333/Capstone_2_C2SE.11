package com.example.ctca.validator;
import com.example.ctca.model.dto.CategoryDTO;
import com.example.ctca.model.entity.Category;
import com.example.ctca.service.CategoryService;
import com.example.ctca.utils.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CategoryValidator implements Validator {

    @Autowired
    private CategoryService categoryService;

    @Override
    public boolean supports(Class<?> clazz) {
        return CategoryDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        try {
            CategoryDTO categoryDTO = (CategoryDTO) target;
            Category category = null;
            // verify name
            if (ValidatorUtil.isEmpty(categoryDTO.getName())) {
                errors.rejectValue("name", "Vui lòng nhập tên Danh Mục!",
                        "Vui lòng nhập tên Danh Mục!");
            } else {
                if (categoryDTO.getName().length()>255){
                    errors.rejectValue("name","Tên không được vượt quá 255 kí tự","Tên không được vượt quá 255 kí tự");
                }
            }

            // verify description
            if (ValidatorUtil.isEmpty(categoryDTO.getDescription())) {
                errors.rejectValue("description", "Vui lòng nhập Mô Tả!",
                        "Vui lòng nhập Mô Tả!");
            }

        } catch (Exception e) {
            errors.rejectValue("msg", "Có lỗi xảy ra, vui lòng thử lại!",
                    "Có lỗi xảy ra, vui lòng thử lại!");
        }
    }
}
