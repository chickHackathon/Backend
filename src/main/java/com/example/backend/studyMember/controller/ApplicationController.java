package com.example.backend.studyMember.controller;

import com.example.backend.common.BaseResponse;
import com.example.backend.common.TokenResolver;
import com.example.backend.member.entity.Member;
import com.example.backend.studyMember.service.ApplicationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class ApplicationController {

    private final ApplicationService applicationService;
    private final TokenResolver tokenResolver;

    @PostMapping("/study/{studyId}/application")
    @Tag(name = "스터디 참여 신청")
    public BaseResponse<Void> createApplication(HttpServletRequest request, @PathVariable Long studyId) {
        Member currentMember = tokenResolver.resolveMemberFromRequest(request);
        applicationService.createApplication(studyId, currentMember);
        return new BaseResponse<>();
    }

    @DeleteMapping("/study/{studyId}/application/member/{memberId}")
    @Tag(name = "방장이 스터디 참여 거절")
    public BaseResponse<Void> deleteApplication(HttpServletRequest request, @PathVariable Long studyId, @PathVariable Long memberId) {
        Member currentMember = tokenResolver.resolveMemberFromRequest(request);
        applicationService.deleteApplication(studyId, memberId, currentMember);
        return new BaseResponse<>();
    }
}
