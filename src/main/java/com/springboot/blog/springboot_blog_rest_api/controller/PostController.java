package com.springboot.blog.springboot_blog_rest_api.controller;

import com.springboot.blog.springboot_blog_rest_api.dto.PostDto;
import com.springboot.blog.springboot_blog_rest_api.dto.PostResponse;
import com.springboot.blog.springboot_blog_rest_api.service.PostService;
import com.springboot.blog.springboot_blog_rest_api.util.AppConstants;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blog")
public class PostController {
    @Autowired
    private PostService postService;

//    http://localhost:8080/api/blog/create/1
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/Create")
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){
        PostDto create = postService.createPost(postDto);
        return new ResponseEntity<>(create, HttpStatus.CREATED);
    }

//    http://localhost:8080/api/blog/get/1
    @GetMapping("/get/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("id") Long postId){
        PostDto postDto = postService.getById(postId);
        return ResponseEntity.ok(postDto);
    }

//    http://localhost:8080/api/blog
    @GetMapping
    public ResponseEntity<PostResponse> getAll(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false)int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ){
        PostResponse postDto = postService.getAll(pageNo,pageSize, sortBy, sortDir);
        return ResponseEntity.ok(postDto);
    }

//  http://localhost:8080/api/blog/update/1
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable("id") Long postId,@Valid @RequestBody PostDto postDto){
        PostDto updatePost = postService.updatePost(postId,postDto);
        return new ResponseEntity<>(updatePost,HttpStatus.OK);
    }

//    http://localhost:8080/api/categories/delete/2
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public String deletePost(@PathVariable("id") Long postId){
        postService.DeletePost(postId);
        return "This post with this Id was delete successfully.";
    }

//    http://localhost:8080/api/blog/category/4
    @GetMapping("/category/{id}")
    public ResponseEntity<List<PostDto>> getAllId(@PathVariable("id") Long categoryId){
        List<PostDto> postDto = postService.getPostByCategory(categoryId);
        return ResponseEntity.ok(postDto);
    }
}
