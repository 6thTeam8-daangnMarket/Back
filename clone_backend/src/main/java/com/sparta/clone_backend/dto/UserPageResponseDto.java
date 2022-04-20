package com.sparta.clone_backend.dto;

import com.sparta.clone_backend.security.UserDetailsImpl;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
public class UserPageResponseDto {

    private String nickName;
    private List<PostListDto> likeposts;
    private int totalpage;


    public UserPageResponseDto(UserDetailsImpl userDetails, Page<PostListDto> userPage) {
        this.nickName = userDetails.getNickName();
        this.likeposts = userPage.getContent();
        this.totalpage = userPage.getTotalPages();
    }
}