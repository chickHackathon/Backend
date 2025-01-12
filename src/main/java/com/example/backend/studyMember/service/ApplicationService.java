package com.example.backend.studyMember.service;

import com.example.backend.member.entity.Member;
import com.example.backend.member.repository.MemberRepository;
import com.example.backend.study.entity.Study;
import com.example.backend.study.repository.StudyRepository;
import com.example.backend.studyMember.dto.ApplicationMemberDto;
import com.example.backend.studyMember.dto.ApplicationStatus;
import com.example.backend.studyMember.dto.ApplicationStudyDto;
import com.example.backend.studyMember.entity.Application;
import com.example.backend.studyMember.repository.ApplicationRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final StudyRepository studyRepository;
    private final MemberRepository memberRepository;

    public void createApplication(Long studyId, Long currentMemberId) {
        Member currentMember = findMember(currentMemberId);
        Study study = findStudy(studyId);
        verifyFirstRequest(studyId, currentMember.getId());
        Application.of(study, currentMember);
        applicationRepository.save(Application.of(study, currentMember));
    }

    private Study findStudy(Long studyId) {
        return studyRepository.findById(studyId).orElseThrow(() -> new IllegalArgumentException("해당 스터디가 존재하지 않습니다."));
    }

    public void deleteApplication(Long studyId, Long applicationId, Long currentMemberId) {
        Member currentMember = findMember(currentMemberId);
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

    private Member findMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 멤버가 존재하지 않습니다."));
    }

    private void verifyFirstRequest(Long studyId, Long applicantId)  {
        if(applicationRepository.findByStudyIdAndApplicantId(studyId, applicantId).isPresent()){
            throw new IllegalArgumentException("이미 신청한 멤버입니다.");
        }

    }

    public List<ApplicationMemberDto> findApplicationMembers(Long currentMemberId) {
        Member member = findMember(currentMemberId);
        List<Study> memberStudies = studyRepository.findByMemberId(member.getId());
        log.info(memberStudies.toString()+":::::::::::"+member.getId());
        List<Application> applications = new ArrayList<>();
        List<ApplicationMemberDto> applicationMembers = new ArrayList<>();

        for(Study one : memberStudies){
            for(Application two : applicationRepository.findByStudyId(one.getId())){
                if(member.getId()==two.getApplicant().getId()){
                    continue;
                }
                applicationMembers.add(
                                ApplicationMemberDto.builder().
                                        memberId(two.getApplicant().getId())
                                        .studyId(one.getId()).build());
            }
            log.info(applicationMembers.toString());
        }


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
