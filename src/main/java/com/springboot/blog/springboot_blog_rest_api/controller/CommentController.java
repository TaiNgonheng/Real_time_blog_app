package com.springboot.blog.springboot_blog_rest_api.controller;

import com.springboot.blog.springboot_blog_rest_api.dto.CommentDto;
import com.springboot.blog.springboot_blog_rest_api.service.CommentService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("create/{id}")
    public ResponseEntity<CommentDto> createComment(@PathVariable("id") Long postId, @RequestBody CommentDto commentDto){
        CommentDto create = commentService.createComment(postId, commentDto);
        return new ResponseEntity<>(create, HttpStatus.CREATED);
    }

    @GetMapping("allComment/{id}")
    public ResponseEntity<List<CommentDto>> getAllComment(@PathVariable("id") Long postId){
        List<CommentDto> getAllComment = commentService.getAllComment(postId);
        return ResponseEntity.ok(getAllComment);
    }

    @GetMapping("{postId}/{id}")
    public ResponseEntity<CommentDto> getCommentWithPostId(@PathVariable("postId") Long postId,
                                                           @PathVariable("id") Long commentId){
        CommentDto commentDto = commentService.getCommentByPostId(postId, commentId);
        return ResponseEntity.ok(commentDto);
    }


}
