package com.sparta.clone_backend.service;

import com.sparta.clone_backend.dto.PostResponseDto;
import com.sparta.clone_backend.model.Post;
import com.sparta.clone_backend.model.PostLike;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PostService {


    // 전체 게시글 조회
    public List<PostResponseDto> getPost(Long userId) {
        User user = new User();
        List<Post> posts = postRepository.findAllByOrderByModifiedAtDesc();
        if(userId != null) {
            user = userRepository.getById(userId);
        }
        List<PostResponseDto> postResponseDtos = new ArrayList<>();
        boolean postLikes;
        for (Post post : posts) {
            int postLikeTotal = postLikeRepository.countByPost(post);

            Optional<PostLike> postLike= postLikeRepository.findByUserAndPost(user, post);
            postLikes = postLike.isPresent();

            PostResponseDto postResponseDto = new PostResponseDto(
                    post.getPostId(),
                    post.getUserId(),
                    post.getContent(),
                    post.getModifiedAt(),
                    post.getImageUrl(),
                    post.getNickName(),
                    postLikeTotal,
                    postLikes
            );
            postResponseDtos.add(postResponseDto);
        }
        return postResponseDtos;
    }


}
