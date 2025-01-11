package com.example.backend.study.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class StudyListReq {
    private String sort = "default"; // 기본 정렬 기준
    private Integer page = 0;        // 기본 페이지 번호

    public StudyListReq(String sort, Integer page) {
        this.sort = (sort != null) ? sort : "default";
        this.page = (page != null) ? page : 0;
    }
}

