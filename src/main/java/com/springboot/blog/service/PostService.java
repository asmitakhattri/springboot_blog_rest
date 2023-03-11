package com.springboot.blog.service;


import com.springboot.blog.entity.Post;
import com.springboot.blog.playload.PostDto;
import com.springboot.blog.playload.PostResponse;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);

    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDto getPostById(long id);

    PostDto updatePost(PostDto postDto, long id);

    void deletePostById(long id);





}
