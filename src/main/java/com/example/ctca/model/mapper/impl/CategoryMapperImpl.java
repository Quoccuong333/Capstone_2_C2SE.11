package com.example.ctca.model.mapper.impl;

import com.example.ctca.model.dto.CategoryDTO;
import com.example.ctca.model.entity.Category;
import com.example.ctca.model.mapper.CategoryMapper;
import com.example.ctca.service.CategoryService;
import com.example.ctca.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategoryMapperImpl implements CategoryMapper {

    @Autowired
    CategoryService categoryService;

    @Autowired
    PostService postService;

    @Override
    public CategoryDTO toDTO(Category category) {
        if (category == null){
            return null;
        }
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        categoryDTO.setDescription(category.getDescription());
        categoryDTO.setStatus(category.isStatus());

        categoryDTO.setAmountPost(postService.findByCategory(category).size());

        return categoryDTO;
    }

    @Override
    public List<CategoryDTO> toListDTO(List<Category> categoryList) {
        if (categoryList == null) {
            return null;
        }
        List<CategoryDTO> list = new ArrayList<>(categoryList.size());
        for (Category category : categoryList) {
            CategoryDTO categoryDTO = toDTO(category);
            if (categoryDTO != null) {
                list.add(categoryDTO);
            }
        }
        return list;
    }

    @Override
    public Category toEntity(CategoryDTO categoryDTO) {
        if (categoryDTO == null)
            return null;

        Category category = categoryService.findById(categoryDTO.getId());
        if (category == null)
            category = new Category();

        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        category.setStatus(categoryDTO.isStatus());

        return category;
    }
}
