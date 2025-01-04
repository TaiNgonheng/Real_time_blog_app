package com.springboot.blog.springboot_blog_rest_api.controller;

import com.springboot.blog.springboot_blog_rest_api.dto.PostDto;
import com.springboot.blog.springboot_blog_rest_api.dto.PostResponse;
import com.springboot.blog.springboot_blog_rest_api.service.PostService;
import com.springboot.blog.springboot_blog_rest_api.util.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(
        name = "CRUD REST APIs for Post Resource"
)
public class PostController {
    @Autowired
    private PostService postService;

//    http://localhost:8080/api/blog/create/1
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(
            name="Bear Authentication"
    )
    @Operation(
            summary = "Create post Rest API ",
            description = "Create Post Rest API is used to save post to database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http status 201 CREATED"
    )
    @PostMapping("/Create")
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){
        PostDto create = postService.createPost(postDto);
        return new ResponseEntity<>(create, HttpStatus.CREATED);
    }

//    http://localhost:8080/api/blog/get/1
    @GetMapping("/get/{id}")
    @Operation(
            summary = "get post by Id Rest API ",
            description = "Get Post by Id API is used to get post by id from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http status 200 SUCCESS"
    )
    public ResponseEntity<PostDto> getPostById(@PathVariable("id") Long postId){
        PostDto postDto = postService.getById(postId);
        return ResponseEntity.ok(postDto);
    }

//    http://localhost:8080/api/blog
    @GetMapping
    @Operation(
            summary = "Get all post Rest API ",
            description = "Get All Post API is used to get all post from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http status 200 SUCCESS"
    )
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
    @SecurityRequirement(
            name="Bear Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Update post by Id Rest API ",
            description = "Update Post by Id API is used to Update post by id from database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http status 201 CREATED"
    )
    @PutMapping("/update/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable("id") Long postId,@Valid @RequestBody PostDto postDto){
        PostDto updatePost = postService.updatePost(postId,postDto);
        return new ResponseEntity<>(updatePost,HttpStatus.OK);
    }

//    http://localhost:8080/api/categories/delete/2
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Delete post by Id Rest API ",
            description = "Delete Post by Id API is used to Delete post by id from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http status 200 SUCCESS"
    )
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
