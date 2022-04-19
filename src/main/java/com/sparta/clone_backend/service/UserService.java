package com.sparta.clone_backend.service;


import com.sparta.clone_backend.dto.IsLoginDto;
import com.sparta.clone_backend.dto.SignupRequestDto;
import com.sparta.clone_backend.model.User;
import com.sparta.clone_backend.repository.UserRepository;
import com.sparta.clone_backend.security.UserDetailsImpl;
import com.sparta.clone_backend.validator.UserInfoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passWordEncoder;
    private final UserInfoValidator userInfoValidator;

    //회원가입
   @Transactional
    public String  registerUser(SignupRequestDto signupRequestDto){
        String message = userInfoValidator.getValidMessage(signupRequestDto);
        if(message.equals("회원가입 성공")){
            String userName = signupRequestDto.getUserName();
            //비밀번호 암호화
            String passWordEncode = passWordEncoder.encode(signupRequestDto.getPassWord());
            //저장할 유저 객체 생성
            User user = new User(userName, signupRequestDto, passWordEncode);
            //회원정보 저장
             userRepository.save(user);
             return "회원가입 성공";
        }else{
            return "회원가입 실패";
        }
    }

    //아이디 중복체크
    public HashMap<String, String> idDuplichk(String userName){
       HashMap<String, String> hashMap = new HashMap<>();
       if(userRepository.findByUserName(userName).isPresent()){
           hashMap.put("status", "400");
//           hashMap.put("msg", "중복된 아이디입니다");
           return hashMap;
       }else{
           hashMap.put("status", "OK");
//           hashMap.put("msg", "사용가능한 아이디입니다");
           return hashMap;
       }

    }

    // 닉네임 중복체크
    public HashMap<String, String> nickNameDuplichk(String nickName){
       HashMap<String, String> hashMap = new HashMap<>();
       if(userRepository.findByNickName(nickName).isPresent()){
           hashMap.put("status", "400");
//           hashMap.put("msg", "중복된 닉네임입니다");
           return hashMap;
       }else{
           hashMap.put("status", "OK");
//           hashMap.put("msg", "사용가능한 닉네임입니다");
           return hashMap;
       }
    }


    //로그인 확인
    public IsLoginDto isloginChk(UserDetailsImpl userDetails){
       String userName = userDetails.getUsername();
       String nickName = userDetails.getNickName();
       Optional<User> user = userRepository.findByUserName(userName);
       IsLoginDto isLoginDto = IsLoginDto.builder()
               .userId(user.get().getId())
               .userName(userName)
               .nickName(nickName)
               .location(user.get().getLocation())
               .build();
       return isLoginDto;
    }

}
