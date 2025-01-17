package com.example.backend.study.service;

import com.example.backend.category.Category;
import com.example.backend.member.entity.Member;
import com.example.backend.member.repository.MemberRepository;
import com.example.backend.study.dto.*;
import com.example.backend.study.entity.Study;
import com.example.backend.study.repository.StudyRepository;
import com.example.backend.studyMember.entity.Recruitment;
import com.example.backend.studyMember.repository.RecruitmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
@RequiredArgsConstructor
public class StudyService {
    private final StudyRepository studyRepository;
    private final MemberRepository memberRepository;
    private final RecruitmentRepository recruitmentRepository;

    public StudyCreateRes register(StudyCreateReq request, String uploadedImgUrl, Long currentMemberId) {
        // Member 조회 (스터디장 찾기)
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        // Study 엔티티 생성
        Study study = Study.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .category(Category.valueOf(String.valueOf(request.getCategory()))) // Enum 값으로 변환
                .deadline(request.getDeadline()) // 요청 데이터에서 마감일 설정
                .studyTime(request.getStudyTime()) // 요청 데이터에서 스터디 시간 설정
                .member(member) // 스터디장 설정
                .Img(uploadedImgUrl) // 업로드된 이미지 URL 설정
                .finish(false) // 기본값 설정
                .build();

        // 저장
        study = studyRepository.save(study);

        // 응답 DTO 반환
        return StudyCreateRes.builder()
                .studyId(study.getId())
                .build();
    }
    // 조회
    public List<StudyListRes> list(StudyListReq request) {
        // 데이터베이스에서 id 기준으로 정렬된 데이터 조회
        List<Study> studyEntities = studyRepository.findAllByOrderByIdAsc();

        // StudyEntity를 StudyListRes로 변환
        return studyEntities.stream()
                .map(entity -> StudyListRes.builder()
                        .id(entity.getId())
                        .title(entity.getTitle())
                        .img(entity.getImg())
                        .content(entity.getContent())
                        .category(String.valueOf(entity.getCategory()))
                        .finish(entity.isFinish())
                        .deadLine(entity.getDeadline())
                        .studyTime(entity.getStudyTime())
                        .location(entity.getMember().getLocation())
                        .build())
                .collect(Collectors.toList());
    }

    public List<StudyMyListRes> getMyStudies(Long currentMemberId) {
        Member member = memberRepository.findById(currentMemberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        // 내가 만든 스터디 조회 (스터디장이 나인 경우)
        List<Study> createdStudies = studyRepository.findByMemberId(currentMemberId);

        // 내가 참여한 스터디 조회 (Recruitment 테이블에서 applicant_id로 검색)
        List<Study> joinedStudies = recruitmentRepository.findByApplicantId(currentMemberId)
                .stream()
                .map(Recruitment::getStudy)
                .collect(Collectors.toList());

        // 두 리스트 합치기 (중복 제거)
        List<Study> allStudies = Stream.concat(createdStudies.stream(), joinedStudies.stream())
                .distinct()
                .collect(Collectors.toList());

        // 응답 객체로 변환
        return allStudies.stream()
                .map(study -> new StudyMyListRes(
                        study.getId(),
                        study.getTitle(),
                        study.getImg(),
                        study.getContent(),
                        study.getCategory() != null ? String.valueOf(study.getCategory()) : null, // Enum 값을 문자열로 변환
                        study.isFinish()
                ))
                .collect(Collectors.toList());

    }

    public StudyDetailRes getStudyDetail(Long studyId) {
        Study study = studyRepository.findById(studyId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 스터디입니다."));
        long c = recruitmentRepository.countByStudyId(studyId);
        return StudyDetailRes.from(study, c);
    }
}

