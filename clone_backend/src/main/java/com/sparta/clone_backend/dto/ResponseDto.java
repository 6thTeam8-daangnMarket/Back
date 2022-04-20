package com.sparta.clone_backend.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResponseDto {
    private boolean result;

    public ResponseDto(boolean result) {
        this.result = result;
    }

}
