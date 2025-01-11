package com.example.backend.study.repository;

import com.example.backend.study.entity.Study;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudyRepository extends JpaRepository<Study, Long> {
    List<Study> findAllByOrderByIdAsc();
    // 내가 만든 스터디 조회
    List<Study> findByMemberId(Long memberId);

}
