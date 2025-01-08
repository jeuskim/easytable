package com.example.easytable.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDTO {
    private int userId;
    private String name;
    private String email;
    private String password;
    private String phone;
    private String role;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
