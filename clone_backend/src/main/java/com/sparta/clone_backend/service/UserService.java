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
    private final PasswordEncoder passWordEncoder;

    //회원가입
   @Transactional
    public User registerUser(SignupRequestDto signupRequestDto){
        String userName = signupRequestDto.getUserName();
        //비밀번호 암호화
        String passWordEncode = passWordEncoder.encode(signupRequestDto.getPassWord());

        //저장할 유저 객체 생성
        User user = new User(userName, signupRequestDto.getNickName(), passWordEncode);
        //회원정보 저장
        return userRepository.save(user);
    }

    //아이디 중복체크
    public HashMap<String, String> idDuplichk(String userName){
       HashMap<String, String> hashMap = new HashMap<>();
       if(userRepository.findByUserName(userName).isPresent()){
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
    public HashMap<String, String> nickNameDuplichk(String nickName){
       HashMap<String, String> hashMap = new HashMap<>();
       if(userRepository.findByNickName(nickName).isPresent()){
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
       String userName = userDetails.getUsername();
       String nickName = userDetails.getNickName();
       DuplicateChkDto duplicateChkDto = new DuplicateChkDto(userName, nickName);
       return duplicateChkDto;
    }



}
