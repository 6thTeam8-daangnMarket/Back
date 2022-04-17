package com.sparta.clone_backend.controller;


import com.sparta.clone_backend.dto.DuplicateChkDto;
import com.sparta.clone_backend.dto.SignupRequestDto;
import com.sparta.clone_backend.model.User;
import com.sparta.clone_backend.security.UserDetailsImpl;
import com.sparta.clone_backend.service.UserService;
import com.sparta.clone_backend.utils.StatusMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.Charset;
import java.util.HashMap;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    // 회원가입
    @PostMapping("/user/signup")
    public ResponseEntity<StatusMessage> registerUser(@RequestBody SignupRequestDto signupRequestDto){
        StatusMessage statusMessage = new StatusMessage();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        statusMessage.setStatus(StatusMessage.StatusEnum.OK);
        statusMessage.setMessage("회원 등록 성공");
        statusMessage.setData(userService.registerUser(signupRequestDto));
        return new ResponseEntity<>(statusMessage, httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/user/idCheck")
    private HashMap<String, String> userDupliChk(@RequestBody DuplicateChkDto duplicateChkDto){
        System.out.println("아이디 중복 확인"+duplicateChkDto.getUserName());
        return userService.idDuplichk(duplicateChkDto.getUserName());
    }

    @PostMapping("/user/nickNameCheck")
    private HashMap<String, String> nickNameDupliChk(@RequestBody DuplicateChkDto duplicateChkDto){
        System.out.println("닉네임 중복 확인" + duplicateChkDto.getNickName());
        return userService.nickNameDuplichk(duplicateChkDto.getNickName());
    }

    @GetMapping("/user/islogin")
    private DuplicateChkDto isloginChk(@AuthenticationPrincipal UserDetailsImpl userDetails){
        System.out.println(userDetails.getUsername());
        System.out.println(userDetails.getNickName());
        return userService.isloginChk(userDetails);
    }

}
