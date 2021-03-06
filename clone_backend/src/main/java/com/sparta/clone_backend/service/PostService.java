package com.sparta.clone_backend.service;


import com.amazonaws.services.s3.AmazonS3Client;
import com.sparta.clone_backend.dto.*;

import com.sparta.clone_backend.model.Image;
import com.sparta.clone_backend.model.Post;

import com.sparta.clone_backend.model.PostLike;
import com.sparta.clone_backend.model.User;
import com.sparta.clone_backend.repository.ImageRepository;
import com.sparta.clone_backend.repository.PostLikeRepository;
import com.sparta.clone_backend.repository.PostRepository;
import com.sparta.clone_backend.repository.UserRepository;
import com.sparta.clone_backend.security.UserDetailsImpl;
import com.sparta.clone_backend.validator.UserInfoValidator;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    private final AmazonS3Client amazonS3Client;
    private final S3Uploader s3Uploader;
    private final UserInfoValidator validator;

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
                        .location(user.getLocation())
                        .nickName(user.getNickName())
                        .category(postRequestDto.getCategory())
                        .createdAt(LocalDateTime.now())
                        .modifiedAt(LocalDateTime.now())
                        .build();

        postRepository.save(post);

        return PostResponseDto.builder()

                .userName(user.getUserName())
                .build();
    }

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;  // S3 버킷 이름

    // 게시글 삭제
    @Transactional
    public Object deletePost(Long postId, User user) {

        Post post = postRepository.findByIdAndUserId(postId,user.getId()).orElseThrow(
                () -> new IllegalArgumentException("작성자만 삭제 가능합니다.")
        );

//         S3 이미지 삭제
        String temp = post.getImageUrl();
        Image image = imageRepository.findByImageUrl(temp);
        String fileName = image.getFilename();
        s3Uploader.deleteImage(fileName);

        postLikeRepository.deleteAllByPostId(postId);
        postRepository.deleteById(post.getId());

        return null;
    }

//    //전체 게시글 조회
//    public Page<PostListDto> getPost(Pageable pageable) {
//
//        List<Post> posts = postRepository.findAllByOrderByModifiedAtDesc();
//        List<PostListDto> postsResponseDtos = new ArrayList<>();
//
//        for (Post post : posts) {
//
//            PostListDto postsResponseDto = new PostListDto(
//                    post.getPostTitle(),
//                    post.getImageUrl(),
//                    post.getPrice(),
//                    post.getLocation(),
//                    convertLocaldatetimeToTime(post.getCreatedAt()),
//                    convertLocaldatetimeToTime(post.getModifiedAt()),
//                    post.getId(),
//                    postLikeRepository.countByPost(post),
//                    post.getCategory());
//            postsResponseDtos.add(postsResponseDto);
//        }
//        Page<PostListDto> postspage = postRepository.findAllByOrderByModifiedAtDesc(pageable);
//       return postspage;
//    }

    //상세 게시글 조회
    public PostDetailResponseDto getPostDetail(Long postId) {
        Post post = postRepository.findById(postId).get();

        return new PostDetailResponseDto(
                post.getPostTitle(),
                post.getPostContents(),
                post.getImageUrl(),
                post.getPrice(),
                post.getUser().getLocation(),
                convertLocaldatetimeToTime(post.getCreatedAt()),
                postLikeRepository.countByPost(post),
                post.getNickName(),
                post.getCategory()
        );
    }

    // 유저 페이지,장바구니 조회
    public UserPageResponseDto getUserPage(UserDetailsImpl userDetails) {
        String userName = userDetails.getUser().getUserName();

        List<PostLike> postLikeObjects = postLikeRepository.findAllByUserName(userName);

        List<Post> posts = postRepository.findAllByOrderByCreatedAtDesc();

        List<PostListDto> postsResponseDtos = new ArrayList<>();

        for (PostLike postLikeObject : postLikeObjects) {
            Post likedPost = postLikeObject.getPost();

            PostListDto postsResponseDto = new PostListDto(
                    likedPost.getId(),
                    likedPost.getPostTitle(),
                    likedPost.getImageUrl(),
                    likedPost.getPrice(),
                    likedPost.getLocation(),
                    convertLocaldatetimeToTime(likedPost.getCreatedAt()),
                    convertLocaldatetimeToTime(likedPost.getModifiedAt()),
                    postLikeRepository.countByPost(likedPost),
                    likedPost.getCategory()
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
        post.update(postId, requestDto.getPostContents());

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

    // 전체 게시글 조회 - 페이징 처리 완료, 시간 변경 실패(몇 초 전, 몇 분 전 변경 필요)
    public Page<PostListDto> showAllPost(int pageno) {
        List<Post> postList = postRepository.findAllByOrderByCreatedAtDesc();
        Pageable pageable = getPageable(pageno);
        List<PostListDto> postListDto = new ArrayList<>();
        forpostList(postList, postListDto);

        int start = pageno * 10;
        int end = Math.min((start + 10), postList.size());

        return validator.overPages(postListDto, start, end, pageable, pageno);
    }

    private Pageable getPageable(int page) {
        Sort.Direction direction = Sort.Direction.DESC;
        Sort sort = Sort.by(direction, "id");
        return PageRequest.of(page, 10, sort);
    }

    private void forpostList(List<Post> postList, List<PostListDto> postListDto) {
        for (Post post : postList) {
            int like = postLikeRepository.countAllByPostId(post.getId());

            PostListDto postDto = new PostListDto(post.getId(), post.getPostTitle(), post.getImageUrl(),
                    post.getPrice(), post.getLocation(), convertLocaldatetimeToTime(post.getCreatedAt()), convertLocaldatetimeToTime(post.getModifiedAt()), like, post.getCategory());

            postListDto.add(postDto);
        }
    }
}

