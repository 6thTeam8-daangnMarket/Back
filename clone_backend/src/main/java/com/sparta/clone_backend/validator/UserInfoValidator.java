package com.sparta.clone_backend.validator;

import com.sparta.clone_backend.dto.PostListDto;
import com.sparta.clone_backend.dto.SignupRequestDto;
import com.sparta.clone_backend.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@RestControllerAdvice
@Component
public class UserInfoValidator {

    private final UserRepository userRepository;

    public UserInfoValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String getValidMessage(SignupRequestDto signupRequestDto) {

        if (!signupRequestDto.getPassWord().equals(signupRequestDto.getPassWordCheck())) {
            return "비밀번호가 일치하지 않습니다";
        } else if (signupRequestDto.getUserName().contains(signupRequestDto.getPassWord())) {
            return "비밀번호는 아이디를 포함할 수 없습니다.";
        } else if (userRepository.findByUserName(signupRequestDto.getUserName()).isPresent()) {
            return "중복된 아이디 입니다.";
        } else if (userRepository.findByNickName(signupRequestDto.getNickName()).isPresent()) {
            return "중복된 닉네임 입니다.";
        } else {
            return "회원가입 성공";
        }
    }

    // 회원가입 시, 유효성 체크
    public Map<String, String> validateHandling(Errors errors) {
        Map<String, String> validatorResult = new HashMap<>();

        for (FieldError error : errors.getFieldErrors()) {
            String validKeyName = "message";
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }
        return validatorResult;
    }

    public Page<PostListDto> overPages(List<PostListDto> postList, int start, int end, Pageable pageable, int page) {
        Page<PostListDto> pages = new PageImpl<>(postList.subList(start, end), pageable, postList.size());
        if(page > pages.getTotalPages()){
            throw new IllegalArgumentException("요청할 수 없는 페이지 입니다.");
        }
        return pages;
    }


}