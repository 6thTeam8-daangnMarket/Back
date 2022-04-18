package com.sparta.clone_backend.controller;


import com.sparta.clone_backend.dto.DuplicateChkDto;
import com.sparta.clone_backend.dto.IsLoginDto;
import com.sparta.clone_backend.dto.SignupRequestDto;
import com.sparta.clone_backend.model.User;
import com.sparta.clone_backend.security.UserDetailsImpl;
import com.sparta.clone_backend.service.UserService;
import com.sparta.clone_backend.utils.StatusMessage;
import com.sparta.clone_backend.validator.UserInfoValidator;
import lombok.RequiredArgsConstructor;
import org.hibernate.PropertyValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
import java.util.HashMap;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

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
    @PostMapping("/user/signUp")
    public ResponseEntity<StatusMessage> registerUser(@RequestBody SignupRequestDto signupRequestDto) {
        System.out.println(signupRequestDto);
        StatusMessage statusMessage = new StatusMessage();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
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

    @PostMapping("/user/idCheck")
    private ResponseEntity<StatusMessage> userDupliChk(@RequestBody DuplicateChkDto duplicateChkDto){
        StatusMessage statusMessage = new StatusMessage();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        statusMessage.setStatus(StatusMessage.StatusEnum.OK);
//        statusMessage.setMessage("회원 등록 성공");
        statusMessage.setData( userService.idDuplichk(duplicateChkDto.getUserName()));

        return new ResponseEntity<>(statusMessage, httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/user/nickNameCheck")
    private ResponseEntity<StatusMessage> nickNameDupliChk(@RequestBody DuplicateChkDto duplicateChkDto){
        StatusMessage statusMessage = new StatusMessage();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        statusMessage.setStatus(StatusMessage.StatusEnum.OK);
//        statusMessage.setMessage("회원 등록 성공");
        statusMessage.setData(userService.nickNameDuplichk(duplicateChkDto.getNickName()));
        return new ResponseEntity<>(statusMessage, httpHeaders, HttpStatus.OK);
    }

    @GetMapping("/user/isLogIn")
    private IsLoginDto isloginChk(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return userService.isloginChk(userDetails);
    }

}
