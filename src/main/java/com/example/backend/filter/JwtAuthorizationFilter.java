package com.example.backend.filter;


import com.example.backend.common.BaseResponse;
import com.example.backend.member.entity.SecurityUser;
import com.example.backend.member.service.MemberService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final HttpServletRequest req;
    private final HttpServletResponse resp;
    private final MemberService memberService;
    @Override
    @SneakyThrows
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        if (request.getRequestURI().equals("/member/login") || request.getRequestURI().equals("/member/logout")) {
            filterChain.doFilter(request, response);
            return;
        }
        String accessToken = _getCookie("accessToken");
        // accessToken 검증 or refreshToken 발급
        if (!accessToken.isBlank()) {
            // 토큰 유효기간 검증
            if (!memberService.validateToken(accessToken)) {
                String refreshToken = _getCookie("refreshToken");
                BaseResponse<String> baseResponse = new BaseResponse(memberService.refreshAccessToken(refreshToken));
                _addHeaderCookie("accessToken", baseResponse.getResult());
            }
            SecurityUser securityUser = memberService.getUserFromAccessToken(accessToken);
            // 인가 처리
            SecurityContextHolder.getContext().setAuthentication(securityUser.genAuthentication());
        }
        filterChain.doFilter(request, response);
    }
    private String _getCookie(String name) {
        Cookie[] cookies = req.getCookies();
        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(name))
                .findFirst()
                .map(Cookie::getValue)
                .orElse("");
    }
    private void _addHeaderCookie(String tokenName, String token) {
        ResponseCookie cookie = ResponseCookie.from(tokenName, token)
                .path("/")
                .sameSite("None")
                .secure(true)
                .httpOnly(true)
                .build();
        resp.addHeader("Set-Cookie", cookie.toString());
    }
}