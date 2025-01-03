package com.springboot.blog.springboot_blog_rest_api.controller;

import com.springboot.blog.springboot_blog_rest_api.dto.CommentDto;
import com.springboot.blog.springboot_blog_rest_api.service.CommentService;
import jakarta.validation.Valid;
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

//  http://localhost:8080/api/comment/create/1
    @PostMapping("create/{id}")
    public ResponseEntity<CommentDto> createComment(@PathVariable("id") Long postId, @Valid @RequestBody CommentDto commentDto){
        CommentDto create = commentService.createComment(postId, commentDto);
        return new ResponseEntity<>(create, HttpStatus.CREATED);
    }

//    http://localhost:8080/api/comment/allComment/1
    @GetMapping("allComment/{id}")
    public ResponseEntity<List<CommentDto>> getAllComment(@PathVariable("id") Long postId){
        List<CommentDto> getAllComment = commentService.getAllComment(postId);
        return ResponseEntity.ok(getAllComment);
    }

//    http://localhost:8080/api/comment/1/1
    @GetMapping("{postId}/{id}")
    public ResponseEntity<CommentDto> getCommentWithPostId(@PathVariable("postId") Long postId, @PathVariable("id") Long commentId){
        CommentDto commentDto = commentService.getCommentByPostId(postId, commentId);
        return ResponseEntity.ok(commentDto);
    }

//  http://localhost:8080/api/comment/update/1/1
    @PutMapping("update/{postId}/{id}")
    public ResponseEntity<CommentDto> updateCommentByPostId(@PathVariable Long postId, @PathVariable("id") Long commentId,@Valid @RequestBody CommentDto commentDto){
        CommentDto savedComment = commentService.updateCommentById(postId,commentId,commentDto);
        return ResponseEntity.ok(savedComment);
    }

//    http://localhost:8080/api/comment/delete/1/1
    @DeleteMapping("delete/{postId}/{id}")
    public String deleteCommentById(@PathVariable Long postId,@PathVariable("id") Long commentid){
        commentService.deleteCommentById(postId,commentid);
        return "The Comment with this Id was Delete successfully";
    }
}
