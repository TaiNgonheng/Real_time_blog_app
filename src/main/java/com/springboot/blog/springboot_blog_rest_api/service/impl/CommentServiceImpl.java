package com.springboot.blog.springboot_blog_rest_api.service.impl;

import com.springboot.blog.springboot_blog_rest_api.dto.CommentDto;
import com.springboot.blog.springboot_blog_rest_api.entity.Comment;
import com.springboot.blog.springboot_blog_rest_api.entity.Post;
import com.springboot.blog.springboot_blog_rest_api.exception.BlogAPIException;
import com.springboot.blog.springboot_blog_rest_api.mapper.CommentMapper;
import com.springboot.blog.springboot_blog_rest_api.repository.CommentRepository;
import com.springboot.blog.springboot_blog_rest_api.repository.PostRepository;
import com.springboot.blog.springboot_blog_rest_api.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Override
    public CommentDto createComment(Long postId, CommentDto commentDto) {
        //take post by ID
        Comment comment = CommentMapper.mapToComment(commentDto);

        Post post = postRepository.findById(postId).orElseThrow(()-> new RuntimeException("this post with this Id are not found."));
        //set post to Comment Entity
        comment.setPost(post);
        Comment savedComment = commentRepository.save(comment);
        return CommentMapper.mapToCommentDto(savedComment);
    }

    @Override
    public List<CommentDto> getAllComment(Long postId) {
        List<Comment> comment = commentRepository.getAllCommentByPostId(postId);
        return comment.stream().map(CommentMapper::mapToCommentDto).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentByPostId(Long postId, Long commentId) {

        // retrieve post with post id from database by
        Post post = postRepository.findById(postId).orElseThrow(()-> new RuntimeException("This post with ID are not exist.!"));

        // retrieve comment with id
        Comment findComment = commentRepository.findById(commentId).orElseThrow(()-> new RuntimeException("This Comment Id are not exist."));

        //Check Comment
        if (!findComment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }
        return CommentMapper.mapToCommentDto(findComment);
    }
}
