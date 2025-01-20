package com.example.easytable.dto.user.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UpdateRequest {
    @NotBlank(message = "햔제 패스워드를 입력해주세요.")
    private String nowPassword;
    @NotBlank(message = "바꿀 패스워드를 입력해주세요.")
    private String changePassword;
    @NotBlank(message = "비밀번호 확인을 입력해주세요.")
    private String passwordCheck;

}
