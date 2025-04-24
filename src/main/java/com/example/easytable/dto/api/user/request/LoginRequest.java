package com.example.easytable.dto.api.user.request;

import com.example.easytable.dto.service.request.LoginParam;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    public LoginParam convert() {
        return new LoginParam(this.email, this.password);
    }

}
