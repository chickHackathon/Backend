package com.example.backend.member.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberSignupRequest {
    private String nickname;
    @Email
    private String email;
    private String password;

}
