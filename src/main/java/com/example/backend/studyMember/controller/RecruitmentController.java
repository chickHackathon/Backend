package com.example.backend.studyMember.controller;

import com.example.backend.common.BaseResponse;
import com.example.backend.common.TokenResolver;
import com.example.backend.member.entity.Member;
import com.example.backend.member.service.MemberService;
import com.example.backend.studyMember.service.RecruitmentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping
@RestController
@RequiredArgsConstructor
public class RecruitmentController {

    private final RecruitmentService recruitmentService;
    private final MemberService memberService;
    private final TokenResolver tokenResolver;

    @PostMapping("/study/{studyId}/recruitment/member/{memberId}")
    @Tag(name="스터디 신청 수락")
    public BaseResponse<Void> createRecruitment(
            @RequestParam Long currentMemberId,
            @PathVariable("studyId") Long studyId,
            @PathVariable("memberId") Long memberId
    ) {
        recruitmentService.createRecruitment(studyId, memberId, currentMemberId);
        return new BaseResponse<>();
    }

}
