package com.sparta.clone_backend.service;

import com.sparta.clone_backend.dto.ResponseDto;
import com.sparta.clone_backend.model.Post;
import com.sparta.clone_backend.model.PostLike;
import com.sparta.clone_backend.repository.PostLikeRepository;
import com.sparta.clone_backend.repository.PostRepository;
import com.sparta.clone_backend.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PostLikeService {
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;

    @Transactional
    public ResponseDto likePost(Long postId, UserDetailsImpl userDetails) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("해당 상품이 존재하지 않습니다.")
        );
        Optional<PostLike> postLike = postLikeRepository.findByUserNameAndPost(userDetails.getUsername(), post);

        if (!postLike.isPresent()) {
            PostLike postLikesave = new PostLike(userDetails.getUsername(), post);
            postLikeRepository.save(postLikesave);
            return new ResponseDto(true);
        }
        else {
            postLikeRepository.deleteById(postLike.get().getId());
            return new ResponseDto(false);
        }
    }

}
