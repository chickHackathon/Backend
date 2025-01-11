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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member; // 스터디장

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

    // 참여자 목록 (다대다 관계)
    @ManyToMany
    @JoinTable(
            name = "study_participants",
            joinColumns = @JoinColumn(name = "study_id"),
            inverseJoinColumns = @JoinColumn(name = "member_id")
    )
    private List<Member> participants = new ArrayList<>();

}

