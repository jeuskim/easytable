package com.example.easytable.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class User {
    private int userId;

    private String name;
    private String email;
    private String password;
    private String phone;
    private String role;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
