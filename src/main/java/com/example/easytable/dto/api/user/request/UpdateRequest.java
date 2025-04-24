package com.example.easytable.dto.api.user.request;

import com.example.easytable.dto.service.request.UpdateParam;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRequest {
    @NotBlank(message = "햔제 패스워드를 입력해주세요.")
    private String nowPassword;
    @NotBlank(message = "바꿀 패스워드를 입력해주세요.")
    private String changePassword;
    @NotBlank(message = "비밀번호 확인을 입력해주세요.")
    private String passwordCheck;

    public UpdateParam convert() {
        return new UpdateParam(this.nowPassword, this.changePassword, this.passwordCheck);
    }

}
