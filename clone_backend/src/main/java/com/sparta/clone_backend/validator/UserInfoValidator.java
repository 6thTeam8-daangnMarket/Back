package com.sparta.clone_backend.validator;

import com.sparta.clone_backend.dto.SignupRequestDto;
import com.sparta.clone_backend.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@RestControllerAdvice
@Component
public class UserInfoValidator {

    private final UserRepository userRepository;

    public UserInfoValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String getValidMessage(SignupRequestDto signupRequestDto){
//        String pattern = "^[a-zA-Z0-9]*$";

//        else if (requestDto.getPassword().length() < 6) {
//            return "비밀번호를 6자 이상 입력하세요";
//        }

        if(!signupRequestDto.getPassWord().equals(signupRequestDto.getPassWordCheck())) {
            return "비밀번호가 일치하지 않습니다";
        } else if(signupRequestDto.getUserName().contains(signupRequestDto.getPassWord())) {
            return "비밀번호는 아이디를 포함할 수 없습니다.";
        } else
            return "회원가입 성공";
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


}