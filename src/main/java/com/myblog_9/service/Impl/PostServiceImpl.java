package com.myblog_9.service.Impl;

import com.myblog_9.entity.Post;
import com.myblog_9.exception.ResourceNotFound;
import com.myblog_9.payload.PostDto;
import com.myblog_9.payload.PostResponse;
import com.myblog_9.repository.PostRepository;
import com.myblog_9.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepo;
    private ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepo,ModelMapper modelMapper) {
        this.postRepo = postRepo;
        this.modelMapper= modelMapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post savePost = postRepo.save(post);
        PostDto dto = new PostDto();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setDescription(post.getDescription());
        dto.setContent(post.getContent());
        return dto;
    }

    @Override
    public void deletePostByPostId(int postId) {
        postRepo.deleteById((long) postId);
        postRepo.findById((long) postId).orElseThrow(
                ()-> new ResourceNotFound("Post not found with id:"+ postId));

    }

    @Override
    public PostDto getPostById(int postId) {
        Post post = postRepo.findById((long) postId).orElseThrow(
                () -> new ResourceNotFound("Post not found with id:" + postId)
        );
        return mapToDto(post);
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> all = postRepo.findAll(pageable);
        List<Post> posts = all.getContent();
        List<PostDto> dtos = posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(dtos);
        postResponse.setPageNo(all.getNumber());
        postResponse.setPageSize(all.getSize());
        postResponse.setTotalElements((int)all.getTotalElements());
        postResponse.setTotalPages(all.getTotalPages());
        postResponse.setLast(all.isLast());

        return postResponse;
    }

    @Override
    public PostDto updatePostById(PostDto dto, int postId) {
        Post post = postRepo.findById((long) postId).orElseThrow(
                () -> new ResourceNotFound("Post not found with id:" + postId)
        );
        post.setTitle(dto.getTitle());
        post.setDescription(dto.getDescription());
        post.setContent(dto.getContent());

        Post updatedPost = postRepo.save(post);

        PostDto dto1 = new PostDto();

        dto1.setId(updatedPost.getId());
        dto1.setTitle(updatedPost.getTitle());
        dto1.setDescription(updatedPost.getDescription());
        dto1.setContent(updatedPost.getContent());
        return dto1;
    }

    PostDto mapToDto(Post post){
        PostDto dto = modelMapper.map(post, PostDto.class);
       /* PostDto dto = new PostDto();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setDescription(post.getDescription());
        dto.setContent(post.getContent());*/
        return dto;
    }
}
