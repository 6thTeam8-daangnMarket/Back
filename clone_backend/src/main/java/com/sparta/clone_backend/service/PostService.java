package com.sparta.clone_backend.service;

import com.sparta.clone_backend.dto.PostRequestDto;
import com.sparta.clone_backend.dto.PostResponseDto;
import com.sparta.clone_backend.dto.ResponseDto;
import com.sparta.clone_backend.model.Post;
import com.sparta.clone_backend.model.PostLike;
import com.sparta.clone_backend.model.User;
import com.sparta.clone_backend.repository.PostLikeRepository;
import com.sparta.clone_backend.repository.PostRepository;
import com.sparta.clone_backend.repository.UserRepository;
import com.sparta.clone_backend.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final UserRepository userRepository;

//    @Autowired
//    public PostService(PostRepository postRepository) {
//        this.postRepository = postRepository;
//    };

    public PostResponseDto createPost(PostRequestDto requestDto, User user) {

        Post post = Post.builder()
                        .user(user)
                        .postTitle(requestDto.getPostTitle())
                        .postContents(requestDto.getPostContents())
                        .imageUrl(requestDto.getImageUrl())
                        .price(requestDto.getPrice())
                        .location(requestDto.getLocation())
                        .createDate(Timestamp.valueOf(LocalDateTime.now()))
                        .updateDate(Timestamp.valueOf(LocalDateTime.now()))
                        .nickname(requestDto.getNickname())
                        .build();

        postRepository.save(post);

        return PostResponseDto.builder()

                .username(user.getUsername())
                .build();
    }

    // 게시글 삭제
    @Transactional
    public void deletePost(Long postId, User user) {

        HashMap<String, String> deletePost = new HashMap<>();
        Post post = postRepository.findByIdAndUserId(postId,user.getId()).orElseThrow(
                () -> new IllegalArgumentException("작성자만 삭제 가능합니다.")
        );
        System.out.println("PostService 삭제 기능 postId"+post.getId());

        postRepository.deleteById(post.getId());

    }


}
