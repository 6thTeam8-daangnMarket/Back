package com.sparta.clone_backend.controller;

import com.sparta.clone_backend.security.UserDetailsImpl;
import com.sparta.clone_backend.service.PostLikeService;
import com.sparta.clone_backend.utils.StatusMessage;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.Charset;

@AllArgsConstructor
@RestController
public class PostLikeController {

    private final PostLikeService postLikeService;

    // 관심 상품 등록
    @PostMapping("/api/posts/{postId}/like")
    public ResponseEntity<StatusMessage> likePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        if (userDetails == null) {
//            return ResponseEntity.badRequest("로그인 해주세요오오오옹");
//
//        }
        StatusMessage statusMessage = new StatusMessage();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        statusMessage.setStatus(StatusMessage.StatusEnum.OK);
        statusMessage.setData(postLikeService.likePost(postId, userDetails));
//        return ResponseEntity.ok()
//                .body("좋아요 완료!");
        return new ResponseEntity<>(statusMessage, httpHeaders, HttpStatus.OK);
    }
}
