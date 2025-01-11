package com.example.backend.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class MemberLoginRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String password;
}