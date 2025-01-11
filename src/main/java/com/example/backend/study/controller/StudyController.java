package com.example.backend.study.controller;

import com.example.backend.common.BaseResponse;
import com.example.backend.study.dto.StudyCreateReq;
import com.example.backend.study.dto.StudyCreateRes;
import com.example.backend.study.dto.StudyListReq;
import com.example.backend.study.dto.StudyListRes;
import com.example.backend.study.service.StudyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/study")
@RequiredArgsConstructor
public class StudyController {
    private final StudyService studyService;
    // 스터디 생성
    @PostMapping()
    public BaseResponse<StudyCreateRes> register(
            @RequestBody StudyCreateReq studyCreateRequest) {
        StudyCreateRes studyCreateRes = studyService.register(studyCreateRequest);
        return new BaseResponse<>(studyCreateRes);
    }
    // 스터디 목록 조회
    @GetMapping("/list")
    public BaseResponse<List<StudyListRes>> list(StudyListReq studyListReq) {
        List<StudyListRes> studyList = studyService.list(studyListReq);
        return new BaseResponse<>(studyList);
    }

}