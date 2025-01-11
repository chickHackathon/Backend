package com.example.backend.study.repository;

import com.example.backend.study.entity.study.Study;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyRepository extends JpaRepository<Study, Long> {
}
