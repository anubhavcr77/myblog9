package com.myblog_9.service;

import com.myblog_9.payload.PostDto;
import com.myblog_9.payload.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);

    void deletePostByPostId(int postId);

    PostDto getPostById(int postId);

    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDto updatePostById(PostDto dto, int postId);
}
