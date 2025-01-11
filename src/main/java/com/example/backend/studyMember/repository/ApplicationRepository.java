package com.example.backend.studyMember.repository;

import com.example.backend.studyMember.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    Optional<Application> findByStudyIdAndApplicantId(Long studyId, Long applicantId);

    List<Application> findByStudyId(Long studyId);

    @Query("select a from Application a where a.applicant.id = :applicantId ")
    List<Application> findByApplicantId(@Param("applicantId") Long applicantId);
}
