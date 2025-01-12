package com.example.backend.studyMember.service;

import com.example.backend.member.entity.Member;
import com.example.backend.member.repository.MemberRepository;
import com.example.backend.study.entity.Study;
import com.example.backend.study.repository.StudyRepository;
import com.example.backend.studyMember.dto.ApplicationStatus;
import com.example.backend.studyMember.entity.Application;
import com.example.backend.studyMember.entity.Recruitment;
import com.example.backend.studyMember.repository.ApplicationRepository;
import com.example.backend.studyMember.repository.RecruitmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecruitmentService {

    private final RecruitmentRepository recruitmentRepository;
    private final ApplicationRepository applicationRepository;
    private final StudyRepository studyRepository;
    private final MemberRepository memberRepository;

    public void createRecruitment(Long studyId, Long applicantId, Long currentMemberId) {
        Member currentMember = findMember(currentMemberId);

        Study study = findStudy(studyId);
        Application application = findApplication(studyId, applicantId);
        if(!currentMember.getId().equals(study.getMember().getId())) {
            throw new IllegalArgumentException("권한이 없는 멤버입니다.");
        }
        application.setStatus(ApplicationStatus.ACCEPTED);
        recruitmentRepository.save(Recruitment.of(study, application.getApplicant()));
    }

    private Study findStudy(Long studyId) {
        return studyRepository.findById(studyId)
                .orElseThrow(() -> new IllegalArgumentException("해당 스터디가 존재하지 않습니다."));
    }

    private Application findApplication(Long studyId, Long applicantId)  {
        return applicationRepository.findByStudyIdAndApplicantId(studyId, applicantId)
                .orElseThrow(() -> new IllegalArgumentException("해당 신청이 존재하지 않습니다."));
    }

    private Member findMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 멤버가 존재하지 않습니다."));
    }

}
