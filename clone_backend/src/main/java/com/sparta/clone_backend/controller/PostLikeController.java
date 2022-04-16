package com.sparta.clone_backend.controller;

import com.sparta.clone_backend.security.UserDetailsImpl;
import com.sparta.clone_backend.service.PostLikeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class PostLikeController {

    private final PostLikeService postLikeService;

    // 관심 상품 등록
    @PostMapping("/api/posts/{postId}/like")
    public ResponseEntity<String> likePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        if (userDetails == null) {
//            return ResponseEntity.badRequest("로그인 해주세요오오오옹");
//
//        }
        postLikeService.likePost(postId,userDetails);
        return ResponseEntity.ok()
                .body("좋아요 완료!");
    }

}
