package com.example.backend.common;

import com.example.backend.member.entity.Member;
import com.example.backend.member.jwt.JwtProvider;
import com.example.backend.member.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class TokenResolver {

    private final JwtProvider jwtProvider;
    private final MemberService memberService;

    public Member resolveMemberFromRequest(HttpServletRequest request) {
        String accessToken = getAccessTokenFromCookies(request);
        Map<String, Object> claims = jwtProvider.getClaims(accessToken);
        String username = (String) claims.get("name");
        return memberService.getMember(username);
    }

    private String getAccessTokenFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            throw new IllegalStateException("No cookies found");
        }

        return Arrays.stream(cookies)
                .filter(cookie -> "accessToken".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new IllegalStateException("Access token not found"));
    }
}