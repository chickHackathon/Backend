package com.example.backend.studyMember.entity;

import com.example.backend.member.entity.Member;
import com.example.backend.study.entity.study.Study;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Recruitment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "study_id")
    private Study study;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "applicant_id")
    private Member applicant;

    public static Recruitment of (Study study, Member applicant) {
        return Recruitment.builder()
                .study(study)
                .applicant(applicant)
                .build();
    }
}
