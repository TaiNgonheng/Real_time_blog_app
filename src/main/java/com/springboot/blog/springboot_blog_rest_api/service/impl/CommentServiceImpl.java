package com.springboot.blog.springboot_blog_rest_api.service.impl;

import com.springboot.blog.springboot_blog_rest_api.dto.CommentDto;
import com.springboot.blog.springboot_blog_rest_api.entity.Comment;
import com.springboot.blog.springboot_blog_rest_api.entity.Post;
import com.springboot.blog.springboot_blog_rest_api.exception.BlogAPIException;
import com.springboot.blog.springboot_blog_rest_api.repository.CommentRepository;
import com.springboot.blog.springboot_blog_rest_api.repository.PostRepository;
import com.springboot.blog.springboot_blog_rest_api.service.CommentService;
import org.modelmapper.ModelMapper;
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

    @Autowired
    private ModelMapper mapper;

    @Override
    public CommentDto createComment(Long postId, CommentDto commentDto) {

        Comment comment = mapToComment(commentDto);
        //take post by ID
        Post post = postRepository.findById(postId).orElseThrow(()-> new RuntimeException("this post with this Id are not found."));
        //set post to Comment Entity
        comment.setPost(post);
        Comment savedComment = commentRepository.save(comment);
        return mapToCommentDto(savedComment);
    }

    @Override
    public List<CommentDto> getAllComment(Long postId) {
        List<Comment> comment = commentRepository.getAllCommentByPostId(postId);
        return comment.stream().map(comment1->mapToCommentDto(comment1)).collect(Collectors.toList());
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
        return mapToCommentDto(findComment);
    }
        //Update Comment by take PostId and CommentId
    @Override
    public CommentDto updateCommentById(Long postId, Long commentId, CommentDto commentDto) {
        //take post by postId from post repository
        Post post = postRepository.findById(postId).orElseThrow(()-> new RuntimeException("This post with with this Id are not exist."));
        //take comment by commentId from commentRepository
        Comment findComment = commentRepository.findById(commentId).orElseThrow(()-> new RuntimeException("This Comment with this Id are not Exist."));
        if (!findComment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment are not belong to post.");
        }
        findComment.setName(commentDto.getName());
        findComment.setEmail(commentDto.getEmail());
        findComment.setBody(commentDto.getBody());
        Comment comment = commentRepository.save(findComment);
        return mapToCommentDto(comment);
    }

    @Override
    public void deleteCommentById(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(()-> new RuntimeException("This post with this Id are not exis."));
        Comment findComment = commentRepository.findById(commentId).orElseThrow(()-> new RuntimeException("this commment with chis Id are not exist."));
        if (!findComment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment are not belong in the post");
        }
        commentRepository.delete(findComment);
    }
    public Comment mapToComment(CommentDto commentDto){
        Comment comment = mapper.map(commentDto,Comment.class);
//        comment.setId(commentDto.getId());
//        comment.setName(commentDto.getName());
//        comment.setBody(commentDto.getBody());
//        comment.setEmail(commentDto.getEmail());
        return comment;
    }
    public CommentDto mapToCommentDto(Comment comment){
        CommentDto commentDto= mapper.map(comment,CommentDto.class);
        return commentDto;
    }
}
