package com.sparta.clone_backend.service;

import com.sparta.clone_backend.dto.PostLikeDto;
import com.sparta.clone_backend.dto.ResponseDto;
import com.sparta.clone_backend.model.Post;
import com.sparta.clone_backend.model.PostLike;
import com.sparta.clone_backend.model.User;
import com.sparta.clone_backend.repository.PostLikeRepository;
import com.sparta.clone_backend.repository.PostRepository;
import com.sparta.clone_backend.repository.UserRepository;
import com.sparta.clone_backend.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PostLikeService {
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final UserRepository userRepository;

    @Transactional
    public ResponseDto likePost(Long postId, UserDetailsImpl userDetails) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("판매되지 않는 상품입니다.")
        );
        Optional<PostLike> postLike = postLikeRepository.findByUsernameAndPost(userDetails.getUsername(), post);

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

//    @Transactional
//    public ResponseDto likePost(PostLikeDto postLikeDto) {
//        ResponseDto responseDto = new ResponseDto();
//        Long postId = postLikeDto.getPostId();
//        String username = postLikeDto.getUsername();
//
//        PostLike postLike = postLikeRepository.findByPostIdAndUsername(postId,username).orElse(null);
//
//        try {
//            if (postLike == null) {
//                PostLike saveLike = new PostLike(postId, username);
//                postLikeRepository.save(saveLike);
//            }
//            else {
//                postLikeRepository.deleteById(postLike.getId());
//            }
//
//            responseDto.setResult(true);
//        } catch (Exception e) {
//            responseDto.setResult(false);
//            responseDto.setErrormessage("하나도 안 좋아요오");
//        }
//        return responseDto;
//    }
}
