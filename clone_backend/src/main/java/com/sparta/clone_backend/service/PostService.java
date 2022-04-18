package com.sparta.clone_backend.service;


import com.sparta.clone_backend.dto.*;

import com.sparta.clone_backend.model.Post;

import com.sparta.clone_backend.model.PostLike;
import com.sparta.clone_backend.model.User;
import com.sparta.clone_backend.repository.PostLikeRepository;
import com.sparta.clone_backend.repository.PostRepository;
import com.sparta.clone_backend.repository.UserRepository;
import com.sparta.clone_backend.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


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

    // 게시물 등록
    public PostResponseDto createPost(PostRequestDto postRequestDto, User user) {

        Post post = Post.builder()
                        .user(user)
                        .postTitle(postRequestDto.getPostTitle())
                        .postContents(postRequestDto.getPostContents())
                        .imageUrl(postRequestDto.getImageUrl())
                        .price(postRequestDto.getPrice())
                        .location(postRequestDto.getLocation())
                        .createdAt(LocalDateTime.now())
                        .modifiedAt(LocalDateTime.now())
                        .nickName(postRequestDto.getNickName())
                        .build();

        postRepository.save(post);

        return PostResponseDto.builder()

                .userName(user.getUserName())
                .build();
    }

    // 게시글 삭제
    @Transactional
    public Object deletePost(Long postId, User user) {

        Post post = postRepository.findByIdAndUserId(postId,user.getId()).orElseThrow(
                () -> new IllegalArgumentException("작성자만 삭제 가능합니다.")
        );
        Optional<PostLike> postLike = postLikeRepository.findById(postId);
        postLikeRepository.deleteById(postLike.get().getId());
        postRepository.deleteById(post.getId());

        return null;
    }

    //전체 게시글 조회
    public List<PostsResponseDto> getPost(UserDetailsImpl userDetails) {
        List<Post> posts = postRepository.findAllByOrderByModifiedAtDesc();
        List<PostsResponseDto> postsResponseDtos = new ArrayList<>();
        for (Post post : posts) {

            PostsResponseDto postsResponseDto = new PostsResponseDto(
                    post.getPostTitle(),
                    post.getImageUrl(),
                    post.getPrice(),
                    userDetails.getLocation(),
                    convertLocaldatetimeToTime(post.getCreatedAt()),
                    convertLocaldatetimeToTime(post.getModifiedAt()),
                    post.getId(),
                    postLikeRepository.countByPost(post));
            postsResponseDtos.add(postsResponseDto);
        }
        return postsResponseDtos;
    }

    //상세 게시글 조회
    public PostDetailResponseDto getPostDetail(Long postId, UserDetailsImpl userDetails) {
        Post post = postRepository.findById(postId).get();

        return new PostDetailResponseDto(
                post.getPostTitle(),
                post.getPostContents(),
                post.getImageUrl(),
                post.getPrice(),
                userDetails.getLocation(),
                convertLocaldatetimeToTime(post.getCreatedAt()),
                postLikeRepository.countByPost(post),
                userDetails.getNickName()
        );
    }

    //유저 페이지,장바구니 조회
    public UserPageResponseDto getUserPage(UserDetailsImpl userDetails) {
        String userName = userDetails.getUser().getUserName();

        List<PostLike> postLikeObjects = postLikeRepository.findAllByUserName(userName);
        List<PostsResponseDto> postsResponseDtos = new ArrayList<>();

        for (PostLike postLikeObject : postLikeObjects) {
            Post likedPost = postLikeObject.getPost();

            PostsResponseDto postsResponseDto = new PostsResponseDto(
                    likedPost.getPostTitle(),
                    likedPost.getImageUrl(),
                    likedPost.getPrice(),
                    userDetails.getLocation(),
                    convertLocaldatetimeToTime(likedPost.getCreatedAt()),
                    convertLocaldatetimeToTime(likedPost.getModifiedAt()),
                    likedPost.getId(),
                    postLikeRepository.countByPost(likedPost)
            );
            postsResponseDtos.add(postsResponseDto);

        }
        return new UserPageResponseDto(userDetails.getNickName(), postsResponseDtos);
    }

    // 게시글 수정 (아직은 내용만 수정 가능)
    @Transactional
    public PostResponseDto editPost(Long postId, PostRequestDto requestDto, User user) {

        Post post = postRepository.findByIdAndUserId(postId,user.getId()).orElseThrow(
                () -> new IllegalArgumentException("작성자만 수정 가능합니다.")
        );
        System.out.println(post.getPostContents());
        post.update(postId, requestDto.getPostContents());
        System.out.println(post.getPostContents());

        PostResponseDto responseDto = new PostResponseDto(postId, post.getPostContents());
        return responseDto;
    }

    public static String convertLocaldatetimeToTime(LocalDateTime localDateTime) {
        LocalDateTime now = LocalDateTime.now();

        long diffTime = localDateTime.until(now, ChronoUnit.SECONDS); // now보다 이후면 +, 전이면 -

        int SEC = 60;
        int MIN = 60;
        int HOUR = 24;
        int DAY = 30;
        int MONTH = 12;

        String msg = null;
        if (diffTime < SEC){
            return diffTime + "초전";
        }
        diffTime = diffTime / SEC;
        if (diffTime < MIN) {
            return diffTime + "분 전";
        }
        diffTime = diffTime / MIN;
        if (diffTime < HOUR) {
            return diffTime + "시간 전";
        }
        diffTime = diffTime / HOUR;
        if (diffTime < DAY) {
            return diffTime + "일 전";
        }
        diffTime = diffTime / DAY;
        if (diffTime < MONTH) {
            return diffTime + "개월 전";
        }

        diffTime = diffTime / MONTH;
        return diffTime + "년 전";
    }
}

