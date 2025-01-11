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
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final HttpServletRequest req;
    private final HttpServletResponse resp;
    private final MemberService memberService;
    @Override
    @SneakyThrows
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        if (request.getRequestURI().equals("/member/login") || request.getRequestURI().equals("/member/logout")||
                request.getRequestURI().equals("/member/signup")) {
            filterChain.doFilter(request, response);
            return;
        }
        String accessToken = _getCookie("accessToken");
        // accessToken 검증 or refreshToken 발급
        if (!accessToken.isBlank()) {
            // 토큰 유효기간 검증
            if (!memberService.validateToken(accessToken)) {
                String refreshToken = _getCookie("refreshToken");
                if(memberService.validateToken(refreshToken)){
                    String baseResponse = memberService.refreshAccessToken(refreshToken);
                    _addHeaderCookie("accessToken", baseResponse);
                }
                if(refreshToken.isBlank() || memberService.validateToken(refreshToken)){
                    //refreshToken 없거나 유효성이 없으면
                    throw new IllegalArgumentException("유효성이 없는 토큰입니다.");
                }

            }
            SecurityUser securityUser = memberService.getUserFromAccessToken(accessToken);
            // 인가 처리
            SecurityContextHolder.getContext().setAuthentication(securityUser.genAuthentication());
        }else{
            throw new IllegalArgumentException("토큰이 비어있습니다.");
        }
        filterChain.doFilter(request, response);
    }
    private String _getCookie(String name) {
        Cookie[] cookies = req.getCookies();
        if(cookies==null){
             return "";
        }
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