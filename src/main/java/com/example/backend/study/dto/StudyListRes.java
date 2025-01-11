package com.example.backend.study.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudyListRes {
    private Long id;
    private String title;
    private String img;
    private String content;
    private String category;
    private boolean finish;
}

