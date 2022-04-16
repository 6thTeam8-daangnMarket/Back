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

    public String getValidMessage(SignupRequestDto requestDto, Errors errors) {
        String error = "";
        String pattern = "^[a-zA-Z0-9]*$";
        if (errors.hasErrors()) {
            // 유효성 통과 못한 필드와 메시지를 핸들링
            Map<String, String> validatorResult = validateHandling(errors);
            return validatorResult.get("message");
        }
//        else if (requestDto.getPassword().length() < 6) {
//            return "비밀번호를 6자 이상 입력하세요";
//        }
        else if(checkUsernameDuplicate(requestDto.getUsername())) {
            return "이미 사용중인 아이디입니다.";
        } else if(checkNickNameDuplicate(requestDto.getNickname())) {
            return "이미 사용중인 닉네임입니다.";
        } else if(!requestDto.getPassword().equals(requestDto.getPasswordCheck())) {
            return "비밀번호가 일치하지 않습니다";
        } else if(requestDto.getUsername().contains(requestDto.getPassword())) {
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

    //아이디 중복 체크
    public boolean checkUsernameDuplicate(String username) {
        return userRepository.existsByUsername(username);
    }

    //닉네임 중복 체크
    public boolean checkNickNameDuplicate(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    //아이디 중복 검사
    public String idDueCheck(SignupRequestDto requestDto) {
        if (checkUsernameDuplicate(requestDto.getUsername())) {
            return "이미 사용중인 아이디입니다.";
        }
        return "사용가능한 아이디 입니다.";
    }

}