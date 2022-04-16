package com.sparta.clone_backend.service;

import com.sparta.clone_backend.dto.PostDetailResponseDto;
import com.sparta.clone_backend.dto.PostRequestDto;
import com.sparta.clone_backend.dto.PostResponseDto;
import com.sparta.clone_backend.model.Post;
import com.sparta.clone_backend.model.PostLike;
import com.sparta.clone_backend.model.User;
import com.sparta.clone_backend.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    // 게시글 저장 test
    public Post savePost(PostRequestDto postRequestDto){

        Post post = new Post(postRequestDto);
        return postRepository.save(post);
    }

    // 전체 게시글 조회
    public List<PostResponseDto> getPost() {
        List<Post> posts = postRepository.findAllByOrderByModifiedAtDesc();
        List<PostResponseDto> postResponseDtos = new ArrayList<>();
        for (Post post : posts) {
            int likeCount = 3;
//                    postLikeRepository.countByPost(post);

            PostResponseDto postResponseDto = new PostResponseDto(
                    post.getPostTitle(),
                    post.getImageUrl(),
                    post.getPrice(),
                    post.getLocation(),
                    post.getCreatedAt(),
                    post.getId(),
                    likeCount
            );
            postResponseDtos.add(postResponseDto);
        }
        return postResponseDtos;
    }

    // 상세 게시글 조회
    public PostDetailResponseDto getPostDetail(Long postId, UserDetailsImpl userDetails) {
        Post post = postRepository.findById(postId).get();

            int likeCount = 3;
//            String nickname = "도라에몽";
//                    postLikeRepository.countByPost(post);

        return new PostDetailResponseDto(
                    post.getPostTitle(),
                    post.getPostContents(),
                    post.getImageUrl(),
                    post.getPrice(),
                    post.getLocation(),
                    post.getCreatedAt(),
                    likeCount,
                    userDetails.getNickname()
            );
        }

    }





