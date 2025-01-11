package com.example.backend.studyMember.entity;

import com.example.backend.member.entity.Member;
import com.example.backend.study.entity.Study;
import com.example.backend.studyMember.dto.ApplicationStatus;
import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "study_id")
    private Study study;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "applicant_id")
    private Member applicant;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    public static Application of(Study study, Member applicant){
        return Application.builder()
                .study(study)
                .applicant(applicant)
                .status(ApplicationStatus.WAITING)
                .build();

    }

}
