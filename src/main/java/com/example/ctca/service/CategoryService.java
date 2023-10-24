package com.example.ctca.service;

import com.example.ctca.model.entity.Category;

import java.util.List;

public interface CategoryService {

    List<Category> findAll();

    Category findById(long id);

    Category save(Category category);
}
