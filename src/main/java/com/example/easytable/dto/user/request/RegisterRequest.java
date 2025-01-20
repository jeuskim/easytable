package com.example.easytable.dto.user.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class RegisterRequest {

    @NotBlank(message = "이름을 입력해주세요.")
    private String name;
    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "이메일 형식으로 입력해주세요.")
    private String email;
    @Pattern(
            regexp = "^(?=.*[!@#$%^&*()_+{}\\[\\]:;<>,.?/~`]).{8,}$",
            message = "비밀번호는 최소 8자 이상이며 특수문자를 포함해야 합니다."
    )
    private String password;
    @NotBlank(message = "비밀번호 확인을 입력해주세요")
    private String passwordCheck;
    @Pattern(
            regexp = "^010-\\d{4}-\\d{4}$",
            message = "올바른 번호를 입력해주세요."
    )
    private String phone;


}
