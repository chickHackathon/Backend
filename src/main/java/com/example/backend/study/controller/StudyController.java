package com.example.backend.study.controller;

import com.example.backend.common.BaseResponse;
import com.example.backend.member.entity.Member;
import com.example.backend.study.dto.*;
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
    // 내가 방장이거나 회원인 스터디 목록 조회
    @GetMapping("/mylist")
    public BaseResponse<List<StudyMyListRes>> getMyStudies(@RequestBody StudyMyListReq request) {

        // 서비스 호출
        List<StudyMyListRes> myStudies = studyService.getMyStudies(request);

        // 응답 반환
        return new BaseResponse<>(myStudies);
    }


}