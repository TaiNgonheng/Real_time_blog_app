package com.springboot.blog.springboot_blog_rest_api.service.impl;

import com.springboot.blog.springboot_blog_rest_api.dto.CategoryDto;
import com.springboot.blog.springboot_blog_rest_api.entity.Category;
import com.springboot.blog.springboot_blog_rest_api.repository.CategoryRepository;
import com.springboot.blog.springboot_blog_rest_api.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;
    private ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        Category category = modelMapper.map(categoryDto,Category.class);
        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory,CategoryDto.class);
    }
}
