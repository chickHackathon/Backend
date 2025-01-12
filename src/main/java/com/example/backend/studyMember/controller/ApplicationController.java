package com.example.backend.studyMember.controller;

import com.example.backend.common.BaseResponse;
import com.example.backend.member.service.MemberService;
import com.example.backend.studyMember.dto.ApplicationMemberDto;
import com.example.backend.studyMember.dto.ApplicationStudyDto;
import com.example.backend.studyMember.service.ApplicationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class ApplicationController {

    private final ApplicationService applicationService;;;

    @PostMapping("/study/{studyId}/application")
    @Tag(name = "스터디 참여 신청")
    public BaseResponse<Void> createApplication(@PathVariable Long studyId,
                                                @RequestParam Long currentMemberId) {
        applicationService.createApplication(studyId, currentMemberId);
        return new BaseResponse<>();
    }

    @DeleteMapping("/study/{studyId}/application/member/{memberId}")
    @Tag(name = "방장이 스터디 참여 거절")
    public BaseResponse<String> deleteApplication(@PathVariable Long studyId, @PathVariable Long memberId, @RequestParam Long currentMemberId) {
        applicationService.deleteApplication(studyId, memberId, currentMemberId);
        return new BaseResponse<>("rejected");
    }

    @GetMapping("/study/application/members")
    @Tag(name="방장이 생성한 스터디에 신청을 보낸 지원자들id,스터디id 조회")
    public BaseResponse<List<ApplicationMemberDto>> getApplications(@RequestParam Long currentMemberId){
        List<ApplicationMemberDto> applicationMembers = applicationService.findApplicationMembers(currentMemberId);
        return new BaseResponse<>(applicationMembers);
    }

    @GetMapping("/study/applications")
    @Tag(name="유저가 신청한 스터디 리스트")
    public BaseResponse<List<ApplicationStudyDto>> getMyApplications(@RequestParam Long currentMemberId){
        List<ApplicationStudyDto> memberApplications = applicationService.findMemberApplications(currentMemberId);
        return new BaseResponse<>(memberApplications);
    }
}
