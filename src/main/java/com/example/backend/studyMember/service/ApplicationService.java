package com.example.backend.studyMember.service;

import com.example.backend.member.entity.Member;
import com.example.backend.study.entity.Study;
import com.example.backend.study.repository.StudyRepository;
import com.example.backend.studyMember.dto.ApplicationStatus;
import com.example.backend.studyMember.entity.Application;
import com.example.backend.studyMember.repository.ApplicationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final StudyRepository studyRepository;

    public void createApplication(Long studyId, Member currentMember) {
        Study study = findStudy(studyId);
        verifyFirstRequest(studyId, currentMember.getId());
        Application.of(study, currentMember);
        applicationRepository.save(Application.of(study, currentMember));
    }

    private Study findStudy(Long studyId) {
        return studyRepository.findById(studyId).orElseThrow(() -> new IllegalArgumentException("해당 스터디가 존재하지 않습니다."));
    }

    public void deleteApplication(Long studyId, Long applicationId, Member currentMember) {
        Study study = findStudy(studyId);
        Application application = findApplication(studyId, applicationId);

        if(study.getMember().getId().equals(currentMember.getId())){
            throw new IllegalArgumentException("권한이 없는 멤버입니다..");
        }

        application.setStatus(ApplicationStatus.REJECTED);
    }

    private Application findApplication(Long studyId, Long applicantId)  {
        return applicationRepository.findByStudyIdAndApplicantId(studyId, applicantId)
                .orElseThrow(() -> new IllegalArgumentException("해당 신청이 존재하지 않습니다."));
    }

    private void verifyFirstRequest(Long studyId, Long applicantId)  {
        if(applicationRepository.findByStudyIdAndApplicantId(studyId, applicantId).isPresent()){
            throw new IllegalArgumentException("이미 신청한 멤버입니다.");
        }

    }
}
