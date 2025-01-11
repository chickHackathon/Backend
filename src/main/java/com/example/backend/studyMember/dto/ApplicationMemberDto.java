package com.example.backend.studyMember.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ApplicationMemberDto {
    private Long memberId;
    private Long studyId;
}
