package com.example.backend.member.repository;

import com.example.backend.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {
    Member findByName(String name);
    Optional<Member> findByRefreshToken(String refreshToken);

}
