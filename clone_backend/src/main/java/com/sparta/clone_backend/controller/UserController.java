package com.sparta.clone_backend.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.clone_backend.dto.DuplicateChkDto;
import com.sparta.clone_backend.dto.IsLoginDto;
import com.sparta.clone_backend.dto.SignupRequestDto;
import com.sparta.clone_backend.security.UserDetailsImpl;
import com.sparta.clone_backend.service.KakaoUserService;
import com.sparta.clone_backend.service.UserService;
import com.sparta.clone_backend.utils.StatusMessage;
import org.hibernate.PropertyValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;

@RestController
public class UserController {

    private final UserService userService;
    private final KakaoUserService kakaoUserService;

    @Autowired
    public UserController(UserService userService, KakaoUserService kakaoUserService){
        this.userService = userService;
        this.kakaoUserService = kakaoUserService;
    }


    //오류 처리
    @ExceptionHandler({PropertyValueException.class, IllegalArgumentException.class, RuntimeException.class })
    public ResponseEntity<StatusMessage> nullex(Exception e) {
        System.err.println(e.getClass());
        StatusMessage statusMessage = new StatusMessage();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        statusMessage.setStatus(StatusMessage.StatusEnum.BAD_REQUEST);
        statusMessage.setData(null);
        return new ResponseEntity<>(statusMessage, httpHeaders, HttpStatus.BAD_REQUEST);
    }

    // 회원가입
    @PostMapping("/user/signup")
    public ResponseEntity<StatusMessage> registerUser(@RequestBody SignupRequestDto signupRequestDto) {
        System.out.println(signupRequestDto);
        StatusMessage statusMessage = new StatusMessage();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

//        statusMessage.setMessage("회원 등록 성공");
        statusMessage.setData(null);
        String message = userService.registerUser(signupRequestDto);
        System.out.println(message);
        if (message.equals("회원가입 성공")) {
            statusMessage.setStatus(StatusMessage.StatusEnum.OK);
            return new ResponseEntity<>(statusMessage, httpHeaders, HttpStatus.OK);
        } else {
            statusMessage.setStatus(StatusMessage.StatusEnum.BAD_REQUEST);
            return new ResponseEntity<>(statusMessage, httpHeaders, HttpStatus.BAD_REQUEST);
        }
    }

    //아이디 중복 체크
    @PostMapping("/user/idCheck")
    private ResponseEntity<StatusMessage> userDupliChk(@RequestBody DuplicateChkDto duplicateChkDto){
        StatusMessage statusMessage = new StatusMessage();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        statusMessage.setStatus(StatusMessage.StatusEnum.OK);
//        statusMessage.setMessage("회원 등록 성공");
        statusMessage.setData(null);
        userService.idDuplichk(duplicateChkDto.getUserName());
        return new ResponseEntity<>(statusMessage, httpHeaders, HttpStatus.OK);
    }

    //닉네임 중복 체크
    @PostMapping("/user/nickNameCheck")
    private ResponseEntity<StatusMessage> nickNameDupliChk(@RequestBody DuplicateChkDto duplicateChkDto){
        StatusMessage statusMessage = new StatusMessage();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        statusMessage.setStatus(StatusMessage.StatusEnum.OK);
//        statusMessage.setMessage("회원 등록 성공");
        statusMessage.setData(null);
        userService.nickNameDuplichk(duplicateChkDto.getNickName());
        return new ResponseEntity<>(statusMessage, httpHeaders, HttpStatus.OK);
    }

    //로그인 확인
    @GetMapping("/user/islogin")
    private IsLoginDto isloginChk(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return userService.isloginChk(userDetails);
    }


    //카카오 로그인
    @GetMapping("/user/kakao/callback")
    public String kakaoLogin(@RequestParam String code){
        try {
            kakaoUserService.kakaoLogin(code);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }
}
