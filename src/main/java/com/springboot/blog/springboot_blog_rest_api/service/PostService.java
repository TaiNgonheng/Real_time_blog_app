package com.springboot.blog.springboot_blog_rest_api.service;

import com.springboot.blog.springboot_blog_rest_api.dto.PostDto;
import com.springboot.blog.springboot_blog_rest_api.dto.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);
    PostDto getById(Long postId);
    PostResponse getAll(int pageNo, int pageSize, String sortBy, String sortDir);
    PostDto updatePost(Long postId, PostDto postDto);
    void DeletePost(Long postId);

    List<PostDto> getPostByCategory(Long categoryId);
}
