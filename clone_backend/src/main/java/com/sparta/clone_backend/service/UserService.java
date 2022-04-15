package com.sparta.clone_backend.service;

import com.sparta.clone_backend.dto.ResponseDto;
import com.sparta.clone_backend.dto.SignupRequestDto;
import com.sparta.clone_backend.model.User;
import com.sparta.clone_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    // 회원가입 요청
    public ResponseDto registUser(SignupRequestDto requestDto) {

        ResponseDto responseDto = null;

        String username = requestDto.getUsername();

        String nickname = requestDto.getNickname();

        // 비밀번호 암호화
        String password = passwordEncoder.encode(requestDto.getPassword());

        // 회원 정보 저장
        User user = new User(username, nickname, password);
        userRepository.save(user);
        responseDto = new ResponseDto(true);

        return responseDto;
    }

}
