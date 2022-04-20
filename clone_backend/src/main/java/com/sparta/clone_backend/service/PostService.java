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
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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
    public Page<PostListDto> getUserPage(UserDetailsImpl userDetails, int pageno) {
        String userName = userDetails.getUser().getUserName();

        List<PostLike> postLikes = postLikeRepository.findAllByUserName(userName);

        Pageable pageable = getPageable(pageno);

        List<PostListDto> userLikeList = new ArrayList<>();

        for (PostLike postLikeObject : postLikes) {
            Post likedPost = postLikeObject.getPost();

            PostListDto postsResponseDto = new PostListDto(
                    likedPost,
                    convertLocaldatetimeToTime(likedPost.getCreatedAt()),
                    convertLocaldatetimeToTime(likedPost.getModifiedAt()),
                    postLikeRepository.countByPost(likedPost)
            );
            userLikeList.add(postsResponseDto);

        }
        int start = pageno * 10;
        int end = Math.min((start + 10), userLikeList.size());

        return validator.overPages(userLikeList, start, end, pageable, pageno);
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

    // 전체 게시글 조회 - 페이징 처리 완료
    public Page<PostListDto> showAllPost(int pageno, UserDetailsImpl userDetails) {
        String username = userDetails.getUser().getUserName();

        List<Post> post = postRepository.findAllByOrderByCreatedAtDesc();

        Pageable pageable = getPageable(pageno);
        List<PostListDto> postListDto = new ArrayList<>();
        forpostList(post,username, postListDto);

        int start = pageno * 10;
        int end = Math.min((start + 10), post.size());

        return validator.overPages(postListDto, start, end, pageable, pageno);
    }


    private void forpostList(List<Post> postList, String username, List<PostListDto> postListDto) {
        for (Post post : postList) {
            int likeCount = postLikeRepository.countAllByPostId(post.getId());

            PostListDto postDto = new PostListDto(post, convertLocaldatetimeToTime(post.getCreatedAt()), convertLocaldatetimeToTime(post.getModifiedAt()), likeCount,
                    postLikeRepository.findByUserNameAndPost(username,post).isPresent());

            postListDto.add(postDto);
        }
    }




    //검색한 내용에 대한 정보
    @Transactional
    public Page<PostListDto> getSearchPost(String keyword, UserDetailsImpl userDetails, int pageno) throws UnsupportedEncodingException {
        List<Post> searchedPosts = new ArrayList<>();

        String decodeVal = URLDecoder.decode(keyword, "utf-8");
        String username = userDetails.getUsername();
        System.out.println("1"+username);
        searchedPosts = postRepository.searchByKeyword(decodeVal);
        List<PostListDto> postListDtos = new ArrayList<>();
        for (Post searchedPost : searchedPosts) {

            PostListDto postListDto = new PostListDto(
                    searchedPost.getId(),
                    searchedPost.getPostTitle(),
                    searchedPost.getImageUrl(),
                    searchedPost.getPrice(),
                    searchedPost.getLocation(),
                    convertLocaldatetimeToTime(searchedPost.getCreatedAt()),
                    convertLocaldatetimeToTime(searchedPost.getModifiedAt()),
                    postLikeRepository.countByPost(searchedPost),
                    searchedPost.getCategory(),
                    postLikeRepository.findByUserNameAndPost(username, searchedPost).isPresent()
            );
            postListDtos.add(postListDto);
            System.out.println("2"+postListDto.getPostTitle());
        }
        Pageable pageable = getPageable(pageno);

        forpostList(searchedPosts, username, postListDtos);

        int start = pageno * 10;
        int end = Math.min((start + 10), searchedPosts.size());

        return validator.overPages(postListDtos, start, end, pageable, pageno);
    }

    // 페이징 처리
    private Pageable getPageable(int page) {
        Sort.Direction direction = Sort.Direction.DESC;
        Sort sort = Sort.by(direction, "id");
        return PageRequest.of(page, 10, sort);
    }

    //카테고리별 내용에 대한 정보
    @Transactional
    public Page<PostListDto> getCategoryPost(String category, UserDetailsImpl userDetails, int pageno) throws UnsupportedEncodingException {
        List<Post> searchedPosts = new ArrayList<>();

        String decodeVal = URLDecoder.decode(category, "utf-8");
        searchedPosts = postRepository.searchByCategory(decodeVal);
        String username = userDetails.getUsername();

        List<PostListDto> postListDtos = new ArrayList<>();
        for (Post searchedPost : searchedPosts) {

            PostListDto postListDto = new PostListDto(
                    searchedPost.getId(),
                    searchedPost.getPostTitle(),
                    searchedPost.getImageUrl(),
                    searchedPost.getPrice(),
                    searchedPost.getLocation(),
                    convertLocaldatetimeToTime(searchedPost.getCreatedAt()),
                    convertLocaldatetimeToTime(searchedPost.getModifiedAt()),
                    postLikeRepository.countByPost(searchedPost),
                    searchedPost.getCategory(),
                    postLikeRepository.findByUserNameAndPost(username, searchedPost).isPresent()
            );
            postListDtos.add(postListDto);

        }
        Pageable pageable = getPageable(pageno);

        forpostList(searchedPosts, username, postListDtos);

        int start = pageno * 10;
        int end = Math.min((start + 10), searchedPosts.size());

        return validator.overPages(postListDtos, start, end, pageable, pageno);
    }
}

