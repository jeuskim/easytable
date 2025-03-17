package com.example.easytable.dto.service.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterParam {

    private String name;

    private String email;

    private String password;

    private String passwordCheck;

    private String phone;


}

