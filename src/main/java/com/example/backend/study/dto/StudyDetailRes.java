package com.example.backend.study.dto;

import com.example.backend.study.entity.Study;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@Builder
public class StudyDetailRes {

    private Long id;          // 스터디 ID
    private String title;     // 스터디 제목
    private String img;       // 대표 이미지 URL
    private String content;   // 스터디 내용
    private String category;  // 카테고리 (한글 이름)
    private boolean finish;   // 완료 여부
    private LocalDateTime createdAt;
    private LocalDateTime deadline;
    private LocalDateTime studyTime;
    private long currentMemberNumber;

    public static StudyDetailRes from(Study study, long currentMember){
        return StudyDetailRes.builder()
                .id(study.getId())
                .title(study.getTitle())
                .img(study.getImg())
                .content(study.getContent())
                .category(study.getCategory().getDisplayName())
                .finish(study.isFinish())
                .createdAt(study.getCreatedAt())
                .deadline(study.getDeadline())
                .studyTime(study.getStudyTime())
                .currentMemberNumber(currentMember)
                .build();
    }

}
