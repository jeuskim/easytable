package com.example.easytable.dto.service.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateParam {

    private String nowPassword;

    private String changePassword;

    private String passwordCheck;

}
