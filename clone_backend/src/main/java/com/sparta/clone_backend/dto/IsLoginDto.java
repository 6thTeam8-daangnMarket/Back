package com.sparta.clone_backend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class IsLoginDto {
    private Long userId;
    private String userName;
    private String nickName;
    private String location;

}
