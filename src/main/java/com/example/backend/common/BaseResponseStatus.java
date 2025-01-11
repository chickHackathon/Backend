package com.example.backend.common;

import lombok.Getter;

@Getter
public enum BaseResponseStatus {
    // 모든 요청 성공 1000
    SUCCESS(true, 1000, "요청이 성공하였습니다."),
    USER_NOT_FOUND(false, 1001, "회원이 없습니다"),
    STUDY_NOT_FOUND(false, 1002, "스터디가 없습니다"),
    RECRUITMENT_NOT_FOUND(false, 1003, "모집이 없습니다"),
    APPLICATION_NOT_FOUND(false,1004, " 없습니다"),
    UNAUTHORIZED_MEMBER(false, 1004, "권한이 없습니다"),;


    private final boolean isSuccess;
    private final Integer code;
    private final String message;
    BaseResponseStatus(Boolean isSuccess, Integer code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}