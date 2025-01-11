package com.example.backend.study.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StudyMyListRes {
    private Long id;          // 스터디 ID
    private String title;     // 스터디 제목
    private String img;       // 대표 이미지 URL
    private String content;   // 스터디 내용
    private String category;  // 카테고리 (한글 이름)
    private boolean finish;   // 완료 여부
}
