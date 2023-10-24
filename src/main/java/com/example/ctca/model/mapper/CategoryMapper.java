package com.example.ctca.model.mapper;

import com.example.ctca.model.dto.CategoryDTO;
import com.example.ctca.model.entity.Category;

import java.util.List;

public interface CategoryMapper {

    CategoryDTO toDTO(Category category);

    List<CategoryDTO> toListDTO(List<Category> categoryList);

    Category toEntity(CategoryDTO categoryDTO);
}
