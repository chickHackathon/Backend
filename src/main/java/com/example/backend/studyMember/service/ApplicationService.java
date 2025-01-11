package com.example.backend.studyMember.service;

import com.example.backend.member.entity.Member;
import com.example.backend.study.entity.Study;
import com.example.backend.study.repository.StudyRepository;
import com.example.backend.studyMember.dto.ApplicationMemberDto;
import com.example.backend.studyMember.dto.ApplicationStatus;
import com.example.backend.studyMember.dto.ApplicationStudyDto;
import com.example.backend.studyMember.entity.Application;
import com.example.backend.studyMember.repository.ApplicationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public List<ApplicationMemberDto> findApplicationMembers(Long studyId){
        List<Application> applications = applicationRepository.findByStudyId(studyId);
        List<ApplicationMemberDto> applicationMembers = new ArrayList<>();
        applications.forEach(e->applicationMembers.add(
                ApplicationMemberDto.builder().memberId(e.getApplicant().getId()).build()));

        return applicationMembers;

    }
    public List<ApplicationStudyDto> findMemberApplications(Long memberId){
        List<Application> memberApplications = applicationRepository.findByApplicantId(memberId);

        List<ApplicationStudyDto> memberApplicationDtos = new ArrayList<>();
            memberApplications.forEach(e->memberApplicationDtos.add(ApplicationStudyDto.builder()
                    .status(e.getStatus()).studyId(e.getStudy().getId()).
                    studyImg(e.getStudy().getImg()).studyTime(e.getStudy().getStudyTime()).build()));
            return memberApplicationDtos;
    }
}
