package com.example.backend.member.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberSignupRequest {
    private String name;
    private String nickname;
    @Email
    private String email;
    private String password;

    private String location;
    private float latitude;
    private float longitude;
    private String region_1depth_name;
    private String region_2depth_name;
    private String region_3depth_name;

}
