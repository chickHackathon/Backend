package com.example.backend.studyMember.controller;

import com.amazonaws.Response;
import com.example.backend.common.BaseResponse;
import com.example.backend.common.TokenResolver;
import com.example.backend.member.entity.Member;
import com.example.backend.member.service.MemberService;
import com.example.backend.studyMember.dto.ApplicationMemberDto;
import com.example.backend.studyMember.dto.ApplicationStudyDto;
import com.example.backend.studyMember.service.ApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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
    private final MemberService memberService;
    @PostMapping("/study/{studyId}/application/{memberId}")
    @Tag(name = "스터디 참여 신청")
    public BaseResponse<Void> createApplication(HttpServletRequest request,
                                                @PathVariable Long studyId,
                                                @PathVariable Long memberId) {
        Member currentMember = memberService.findById(memberId).get();
        applicationService.createApplication(studyId, currentMember);
        return new BaseResponse<>();
    }

    @DeleteMapping("/study/{studyId}/application/member/{memberId}")
    @Tag(name = "방장이 스터디 참여 거절")
    public BaseResponse<String> deleteApplication(HttpServletRequest request, @PathVariable Long studyId, @PathVariable Long memberId) {
        Member currentMember = memberService.findById(memberId).get();
        applicationService.deleteApplication(studyId, memberId, currentMember);
        return new BaseResponse<>("rejected");
    }

    @GetMapping("/study/application/members/{memberId}")
    @Tag(name="방장이 생성한 스터디에 신청을 보낸 지원자들id,스터디id 조회")
    public BaseResponse<List<ApplicationMemberDto>> getApplications(@PathVariable Long memberId,
                                                                    HttpServletRequest request){
        Member currentMember = memberService.findById(memberId).get();

        List<ApplicationMemberDto> applicationMembers = applicationService.findApplicationMembers(currentMember);
        return new BaseResponse<>(applicationMembers);
    }
    @GetMapping("/study/applications/{memberId}")
    @Tag(name="유저가 신청한 스터디 리스트")
    public BaseResponse<List<ApplicationStudyDto>> getMyApplications(HttpServletRequest request,@PathVariable Long memberId){
        Member currentMember = memberService.findById(memberId).get();
        List<ApplicationStudyDto> memberApplications = applicationService.findMemberApplications(memberId);
        //memberId로 application 찾으면됨
        return new BaseResponse<>(memberApplications);
    }
}
