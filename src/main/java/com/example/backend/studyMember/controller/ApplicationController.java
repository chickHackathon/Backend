package com.example.backend.studyMember.controller;

import com.example.backend.common.BaseResponse;
import com.example.backend.common.TokenResolver;
import com.example.backend.member.entity.Member;
import com.example.backend.studyMember.dto.ApplicationMemberDto;
import com.example.backend.studyMember.dto.ApplicationStudyDto;
import com.example.backend.studyMember.service.ApplicationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/study/{studyId}/application/members")
    @Tag(name="방장이 생성한 스터디에 신청을 보낸 스터디원을 방장이 조회")
    public BaseResponse<List<ApplicationMemberDto>> getApplications(HttpServletRequest request,@PathVariable Long studyId){
        Member currentMember = tokenResolver.resolveMemberFromRequest(request);
        List<ApplicationMemberDto> applicationMembers = applicationService.findApplicationMembers(studyId);
        //studyId 로 해당 application을 찾으면 됨.
        return new BaseResponse<>(applicationMembers);
    }

    @GetMapping("/study/applications/{memberId}")
    @Tag(name="스터디원 입장에서 신청한 스터디 리스트")
    public BaseResponse<List<ApplicationStudyDto>> getMyApplications(HttpServletRequest request,@PathVariable Long memberId){
        Member currentMember = tokenResolver.resolveMemberFromRequest(request);
        List<ApplicationStudyDto> memberApplications = applicationService.findMemberApplications(memberId);
        //memberId로 application 찾으면됨
        return new BaseResponse<>(memberApplications);
    }
}
