package com.springboot.blog.springboot_blog_rest_api.service.impl;

import com.springboot.blog.springboot_blog_rest_api.dto.PostDto;
import com.springboot.blog.springboot_blog_rest_api.dto.PostResponse;
import com.springboot.blog.springboot_blog_rest_api.entity.Post;
import com.springboot.blog.springboot_blog_rest_api.repository.PostRepository;
import com.springboot.blog.springboot_blog_rest_api.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ModelMapper mapper;

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = mapToPost(postDto);
        Post savedPost = postRepository.save(post);
        return mapToPostDto(savedPost);
    }

    @Override
    public PostDto getById(Long postId) {
        Post getPost = postRepository.findById(postId).get();
        return mapToPostDto(getPost);
    }

    @Override
    public PostResponse getAll(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo,pageSize, sort);

        Page<Post> posts = postRepository.findAll(pageable);
        List<Post> listOfPost = posts.getContent();
        List<PostDto> content = listOfPost.stream().map(posts1->mapToPostDto(posts1)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());
        return postResponse;
    }
    @Override
    public PostDto updatePost(Long postId, PostDto postDto) {
        Post post = postRepository.findById(postId).orElseThrow(()-> new RuntimeException("This Post Id are not found: "+postId));
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        Post savedPost = postRepository.save(post);
        return mapToPostDto(savedPost);
    }
    @Override
    public void DeletePost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(()-> new RuntimeException("This post with this Id are not found."));
        postRepository.delete(post);
    }
    public Post mapToPost(PostDto postDto){
        Post post = mapper.map(postDto,Post.class);
//        post.setId(postDto.getId());
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());
        return post;
    }
    public PostDto mapToPostDto(Post post){
        PostDto postDto = mapper.map(post,PostDto.class);
//        postDto.setId(post.getId());
//        postDto.setTitle(post.getTitle());
//        postDto.setDescription(post.getDescription());
//        postDto.setContent(post.getContent());
        return postDto;
    }
}
