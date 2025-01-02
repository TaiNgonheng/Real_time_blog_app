package com.springboot.blog.springboot_blog_rest_api.controller;

import com.springboot.blog.springboot_blog_rest_api.dto.CategoryDto;
import com.springboot.blog.springboot_blog_rest_api.service.CategoryService;
import com.springboot.blog.springboot_blog_rest_api.service.impl.CategoryServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto){
           CategoryDto savedCate = categoryService.addCategory(categoryDto);
           return new ResponseEntity<>(savedCate, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getById(@PathVariable("id") Long categoryId){
        CategoryDto categoryDto = categoryService.getById(categoryId);
        return ResponseEntity.ok(categoryDto);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<CategoryDto>> getALl(){
        return ResponseEntity.ok(categoryService.getAll());
    }

    @PutMapping("/add/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDto> update(@PathVariable("id") Long categoryId, @RequestBody CategoryDto categoryDto){
        CategoryDto savedCategory = categoryService.updateCate(categoryId,categoryDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> delete(@PathVariable("id") Long categoryId){
        categoryService.Delete(categoryId);
        return ResponseEntity.ok("Category delete sucessfully");
    }
}
