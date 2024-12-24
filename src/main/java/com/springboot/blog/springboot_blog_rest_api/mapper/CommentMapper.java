package com.springboot.blog.springboot_blog_rest_api.mapper;

import com.springboot.blog.springboot_blog_rest_api.dto.CommentDto;
import com.springboot.blog.springboot_blog_rest_api.entity.Comment;
import org.modelmapper.ModelMapper;

public class CommentMapper {
    private ModelMapper mapper;
    public static Comment mapToComment(CommentDto commentDto){
        Comment comment = new Comment();
        comment.setId(commentDto.getId());
        comment.setName(commentDto.getName());
        comment.setBody(commentDto.getBody());
        comment.setEmail(commentDto.getEmail());
        return comment;
    }
    public static CommentDto mapToCommentDto(Comment comment){
        CommentDto commentDto= new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setName(comment.getName());
        commentDto.setBody(comment.getBody());
        commentDto.setEmail(comment.getEmail());
        return commentDto;
    }
}
