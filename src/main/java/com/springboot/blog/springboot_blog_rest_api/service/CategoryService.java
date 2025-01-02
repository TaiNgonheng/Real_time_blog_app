package com.springboot.blog.springboot_blog_rest_api.service;

import com.springboot.blog.springboot_blog_rest_api.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto addCategory(CategoryDto categoryDto);
    CategoryDto getById(Long categoryId);
    List<CategoryDto> getAll();
    CategoryDto updateCate(Long categoryId, CategoryDto categoryDto);
    void Delete(Long categoryId);
}
