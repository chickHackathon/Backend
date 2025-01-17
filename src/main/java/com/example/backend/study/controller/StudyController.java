package com.example.backend.study.controller;

import com.example.backend.common.BaseResponse;
import com.example.backend.file.service.CloudFileUploadService;
import com.example.backend.member.entity.Member;
import com.example.backend.study.dto.*;
import com.example.backend.study.service.StudyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/study")
@RequiredArgsConstructor
public class StudyController {
    private final StudyService studyService;
    private final CloudFileUploadService cloudFileUploadService;
    // 스터디 생성
    @PostMapping()
    public BaseResponse<StudyCreateRes> register(
            @RequestPart StudyCreateReq studyCreateRequest,
            @RequestPart(required=false) MultipartFile Img,
            @RequestParam Long currentMemberId) {
        // 이미지 업로드 후 URL 반환
        String uploadedImgUrl = null;
        if (Img != null) {
            uploadedImgUrl = cloudFileUploadService.uploadImg(Img);
        }
        // 스터디 등록
        StudyCreateRes studyCreateRes = studyService.register(studyCreateRequest, uploadedImgUrl, currentMemberId);
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
    public BaseResponse<List<StudyMyListRes>> getMyStudies(@RequestParam Long currentMemberId) {

        // 서비스 호출
        List<StudyMyListRes> myStudies = studyService.getMyStudies(currentMemberId);

        // 응답 반환
        return new BaseResponse<>(myStudies);
    }

     @GetMapping("/{studyId}")
    public BaseResponse<StudyDetailRes> getStudyDetail(@PathVariable Long studyId) {
        StudyDetailRes studyDetailRes = studyService.getStudyDetail(studyId);
        return new BaseResponse<>(studyDetailRes);
    }

}