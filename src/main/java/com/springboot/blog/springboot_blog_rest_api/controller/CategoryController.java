package com.springboot.blog.springboot_blog_rest_api.controller;

import com.springboot.blog.springboot_blog_rest_api.dto.CategoryDto;
import com.springboot.blog.springboot_blog_rest_api.service.CategoryService;
import com.springboot.blog.springboot_blog_rest_api.service.impl.CategoryServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController {
    private CategoryService categoryService;

    @PostMapping("/add/category")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto){
           CategoryDto savedCate = categoryService.addCategory(categoryDto);
           return new ResponseEntity<>(savedCate, HttpStatus.CREATED);
    }
}
