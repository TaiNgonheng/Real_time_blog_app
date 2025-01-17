package com.springboot.blog.springboot_blog_rest_api.service;

import com.springboot.blog.springboot_blog_rest_api.dto.LoginDto;
import com.springboot.blog.springboot_blog_rest_api.dto.RegisterDto;

import java.util.Set;

public interface AuthService {
    String login(LoginDto loginDto);
    String register(RegisterDto registerDto);
    Set<?> name(LoginDto loginDto);
}
