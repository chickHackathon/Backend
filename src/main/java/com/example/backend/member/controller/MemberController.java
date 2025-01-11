package com.example.backend.member.controller;

import com.example.backend.common.BaseResponse;
import com.example.backend.common.TokenResolver;
import com.example.backend.member.dto.MemberLoginRequest;
import com.example.backend.member.dto.MemberSignupRequest;
import com.example.backend.member.entity.Member;
import com.example.backend.member.jwt.JwtProvider;
import com.example.backend.member.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final JwtProvider jwtProvider;
    private final TokenResolver tokenResolver;

    @PostMapping("/signup")
    public String signup(@RequestBody MemberSignupRequest memberSignupRequest) {
        memberService.signup(memberSignupRequest);
        return "회원가입 성공";
    }


    @PostMapping("/login")
    public BaseResponse<Void> login(@Valid @RequestBody MemberLoginRequest memberLoginRequest, HttpServletResponse response) {

        Member member = memberService.getMember(memberLoginRequest.getName());
        //토큰 생성
        String token = jwtProvider.genAccessToken(member);

        //응답 데이터에 accessToken 이름으로 토큰을 발급.
        Cookie cookie = new Cookie("accessToken", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60);
        response.addCookie(cookie);

        String refreshToken = member.getRefreshToken();
        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(60 * 60);
        response.addCookie(refreshTokenCookie);

        return new BaseResponse<>();
    }

}

