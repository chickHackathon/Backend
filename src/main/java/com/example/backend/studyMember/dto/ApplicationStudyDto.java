package com.example.backend.studyMember.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class ApplicationStudyDto {
    Long studyId;
    String studyImg;
    LocalDateTime studyTime;
    ApplicationStatus status;
}
