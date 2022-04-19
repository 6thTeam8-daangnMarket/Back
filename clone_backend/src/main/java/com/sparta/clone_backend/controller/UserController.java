package com.sparta.clone_backend.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.nimbusds.oauth2.sdk.TokenResponse;
import com.sparta.clone_backend.dto.DuplicateChkDto;
import com.sparta.clone_backend.dto.IsLoginDto;
import com.sparta.clone_backend.dto.KakaoTokenDto;
import com.sparta.clone_backend.dto.SignupRequestDto;
import com.sparta.clone_backend.security.UserDetailsImpl;
import com.sparta.clone_backend.service.KakaoUserService;
import com.sparta.clone_backend.service.UserService;
import com.sparta.clone_backend.utils.StatusMessage;
import jdk.nashorn.internal.parser.Token;
import org.hibernate.PropertyValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import java.nio.charset.Charset;
import java.util.HashMap;

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

        return new ResponseEntity<>(statusMessage, httpHeaders, HttpStatus.BAD_REQUEST);
    }

    // 회원가입
    @PostMapping("/user/signUp")
    public ResponseEntity<String> registerUser(@RequestBody SignupRequestDto signupRequestDto) {
        String message = userService.registerUser(signupRequestDto);
        if (message.equals("회원가입 성공")) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //아이디 중복 체크
    @PostMapping("/user/idCheck")
    private ResponseEntity<StatusMessage> userDupliChk(@RequestBody DuplicateChkDto duplicateChkDto) {
        StatusMessage statusMessage = new StatusMessage();
        HashMap<String, String> hashMap = userService.idDuplichk(duplicateChkDto.getUserName());
        if (hashMap.get("status").equals("OK")) {
            statusMessage.setStatus(StatusMessage.StatusEnum.OK);
            return new ResponseEntity<>(statusMessage, HttpStatus.OK);
        } else {
            statusMessage.setStatus(StatusMessage.StatusEnum.BAD_REQUEST);
            return new ResponseEntity<>(statusMessage, HttpStatus.BAD_REQUEST);
        }
    }

    //닉네임 중복 체크
    @PostMapping("/user/nickNameCheck")
    private ResponseEntity<StatusMessage> nickNameDupliChk(@RequestBody DuplicateChkDto duplicateChkDto){
        StatusMessage statusMessage = new StatusMessage();
        HashMap<String, String> hashMap = userService.nickNameDuplichk(duplicateChkDto.getNickName());
        if(hashMap.get("status").equals("OK")){
            statusMessage.setStatus(StatusMessage.StatusEnum.OK);
            return new ResponseEntity<>(statusMessage, HttpStatus.OK);
        }else{
            statusMessage.setStatus(StatusMessage.StatusEnum.BAD_REQUEST);
            return new ResponseEntity<>(statusMessage, HttpStatus.BAD_REQUEST);
        }
    }


    //로그인 확인

    @GetMapping("/user/isLogIn")
    private ResponseEntity<IsLoginDto> isloginChk(@AuthenticationPrincipal UserDetailsImpl userDetails){
       userService.isloginChk(userDetails);
       return new ResponseEntity<>(userService.isloginChk(userDetails),HttpStatus.OK);


    }


    //카카오 로그인
    @GetMapping("/user/kakao/callback")
    public ResponseEntity<String> kakaoLogin(@RequestParam String code)throws JsonProcessingException{
        HttpHeaders res = new HttpHeaders();
        String msg = "카카오로그인 성공";
        String token = kakaoUserService.kakaoLogin(code);
        res.add("Authorization", token);
        return ResponseEntity.ok().headers(res).body(msg);


    }
}
