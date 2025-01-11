package com.example.backend.studyMember.repository;

import com.example.backend.studyMember.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    Optional<Application> findByStudyIdAndApplicantId(Long studyId, Long applicantId);
}
