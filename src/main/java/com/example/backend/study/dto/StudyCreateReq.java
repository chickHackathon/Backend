package com.example.backend.study.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class StudyCreateReq {
    private Long memberId; // 스터디장 ID
    private String title; // 스터디 제목
    private String content; // 스터디 내용
    private String category; // 카테고리 (Enum 값으로 전달, 예: "TECH")
    private LocalDateTime deadline; // 마감일
    private LocalDateTime studyTime; // 스터디 진행 시간
}

