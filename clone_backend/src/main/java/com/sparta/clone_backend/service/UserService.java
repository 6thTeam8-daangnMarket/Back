package com.sparta.clone_backend.service;


import com.sparta.clone_backend.dto.DuplicateChkDto;
import com.sparta.clone_backend.dto.SignupRequestDto;
import com.sparta.clone_backend.model.User;
import com.sparta.clone_backend.repository.UserRepository;
import com.sparta.clone_backend.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //회원가입
   @Transactional
    public User registerUser(SignupRequestDto signupRequestDto){
        String username = signupRequestDto.getUsername();
        //비밀번호 암호화
        String passwordEncode = passwordEncoder.encode(signupRequestDto.getPassword());
        //저장할 유저 객체 생성
        User user = new User(username, passwordEncode, signupRequestDto.getNickname());
        //회원정보 저장
        return userRepository.save(user);
    }

    //아이디 중복체크
    public HashMap<String, String> idDuplichk(String username){
       HashMap<String, String> hashMap = new HashMap<>();
       if(userRepository.findByUsername(username).isPresent()){
           hashMap.put("result", "true");
//           hashMap.put("msg", "중복된 아이디입니다");
           return hashMap;
       }else{
           hashMap.put("result", "false");
//           hashMap.put("msg", "사용가능한 아이디입니다");
           return hashMap;
       }

    }

    // 닉네임 중복체크
    public HashMap<String, String> nicknameDuplichk(String nickname){
       HashMap<String, String> hashMap = new HashMap<>();
       if(userRepository.findByNickname(nickname).isPresent()){
           hashMap.put("result", "true");
//           hashMap.put("msg", "중복된 닉네임입니다");
           return hashMap;
       }else{
           hashMap.put("result", "false");
//           hashMap.put("msg", "사용가능한 닉네임입니다");
           return hashMap;
       }
    }


    //로그인 확인
    public DuplicateChkDto isloginChk(UserDetailsImpl userDetails){
       String username = userDetails.getUsername();
       String nickname = userDetails.getNickname();
       DuplicateChkDto duplicateChkDto = new DuplicateChkDto(username, nickname);
       return duplicateChkDto;
    }

}
