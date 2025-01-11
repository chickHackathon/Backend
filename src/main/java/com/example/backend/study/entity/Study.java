package com.example.backend.study.entity;

import com.example.backend.category.Category;
import com.example.backend.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Study {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //  대표이미지(s3)

    @Enumerated(EnumType.STRING)
    private Category category;

    @Builder.Default
    @Column(name = "Img")
    private String Img = "";

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "title", length = 100, nullable = false)
    private String title; // varchar(100)

    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    private String content; // text

    @Column(name = "created_at", nullable = false)
    @CreatedDate
    private LocalDateTime createdAt; // datetime

    @Column(name = "deadline", nullable = false)
    @CreatedDate
    private LocalDateTime deadline; // datetime

    @Column(name = "study_time", nullable = false)
    @CreatedDate
    private LocalDateTime studyTime; // datetime

    @Column(name = "finish", nullable = false)
    private boolean finish;



}

