package com.example.backend.studyMember.repository;


import com.example.backend.study.entity.Study;
import com.example.backend.studyMember.entity.Recruitment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Arrays;
import java.util.List;

public interface RecruitmentRepository extends JpaRepository<Recruitment, Long> {
    // 특정 회원이 참여한 스터디 조회
    @Query("SELECT r.study FROM Recruitment r WHERE r.applicant.id = :memberId")
    List<Study> findJoinedStudiesByMemberId(@Param("memberId") Long memberId);

    List<Recruitment> findByApplicantId(Long applicantId);

    Study findByStudyId(Long studyId);

    long countByStudyId(Long studyId);
}
