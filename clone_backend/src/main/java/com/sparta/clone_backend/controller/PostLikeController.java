package com.sparta.clone_backend.controller;

import com.sparta.clone_backend.dto.ResponseDto;
import com.sparta.clone_backend.security.UserDetailsImpl;
import com.sparta.clone_backend.service.PostLikeService;
import com.sparta.clone_backend.utils.StatusMessage;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.Charset;

@AllArgsConstructor
@RestController
public class PostLikeController {

    private final PostLikeService postLikeService;

    // 예외 처리
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<StatusMessage> nullex(Exception e) {
        System.err.println(e.getClass());
        StatusMessage statusMessage = new StatusMessage();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        statusMessage.setStatus(StatusMessage.StatusEnum.BAD_REQUEST);

        return new ResponseEntity<>(statusMessage, httpHeaders, HttpStatus.BAD_REQUEST);
    }

    // 관심 상품 등록
    @PostMapping("/api/posts/{postId}/like")
    public ResponseEntity<ResponseDto> likePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return new ResponseEntity<>(postLikeService.likePost(postId, userDetails), HttpStatus.OK);
    }
}