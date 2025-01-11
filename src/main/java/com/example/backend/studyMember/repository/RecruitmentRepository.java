package com.example.backend.studyMember.repository;


import com.example.backend.studyMember.entity.Recruitment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecruitmentRepository extends JpaRepository<Recruitment, Long> {
}
