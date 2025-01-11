package com.example.backend.member.service;

import com.example.backend.common.BaseResponse;
import com.example.backend.member.dto.MemberSignupRequest;
import com.example.backend.member.entity.Member;
import com.example.backend.member.entity.SecurityUser;
import com.example.backend.member.jwt.JwtProvider;
import com.example.backend.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public Member signup(MemberSignupRequest request) {
        Member member = Member.builder()
                .nickname(request.getNickname())
                .email(request.getEmail())
                .password(request.getPassword())
                .build();
        return memberRepository.save(member);
    }

    public Member join(String name, String password) {
        Member CheckedSignUpMember = memberRepository.findByName(name);

        if (CheckedSignUpMember != null) {
            throw new IllegalArgumentException("이미 존재하는 회원입니다.");
        }

        Member member = Member.builder()
                .name(name)
                .password(passwordEncoder.encode(password))
                .build();

        String refreshToken = jwtProvider.genRefreshToken(member);
        member.setRefreshToken(refreshToken);


        return memberRepository.save(member);
    }

    public Optional<Member> findById(Long id) {
        return memberRepository.findById(id);
    }

    public Member getMember(String username) {
        return memberRepository.findByName(username);
    }
    // 토큰 유효성 검증
    public boolean validateToken(String token) {
        return jwtProvider.verify(token);
    }
    // 토큰갱신
    public String refreshAccessToken(String refreshToken) {
        Member member = memberRepository.findByRefreshToken(refreshToken).orElseThrow(() -> new IllegalArgumentException("유효하지 않은 토큰입니다."));
        String accessToken = jwtProvider.genAccessToken(member);
        return accessToken;
    }
    // 토큰으로 User 정보 가져오기
    public SecurityUser getUserFromAccessToken(String accessToken) {
        Map<String, Object> payloadBody = jwtProvider.getClaims(accessToken);
        long id = (int) payloadBody.get("id");
        String username = (String) payloadBody.get("username");
        List<GrantedAuthority> authorities = new ArrayList<>();
        return new SecurityUser(id, username, "", authorities);
    }


}
