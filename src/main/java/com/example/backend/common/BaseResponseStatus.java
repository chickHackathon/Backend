package com.example.backend.common;

import lombok.Getter;

@Getter
public enum BaseResponseStatus {
    // 모든 요청 성공 1000
    SUCCESS(true, 1000, "요청이 성공하였습니다."),
    USER_NOT_FOUND(false, 1001, "회원이 없습니다");




    private final boolean isSuccess;
    private final Integer code;
    private final String message;
    BaseResponseStatus(Boolean isSuccess, Integer code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}