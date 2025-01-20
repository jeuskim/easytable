package com.example.easytable.entity;

import lombok.*;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import static lombok.AccessLevel.*;

@Entity
@Getter
@Setter
@Table(name = "User")
@NoArgsConstructor(access = PROTECTED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    private String name;
    private String email;
    private String password;
    private String phone;
    private String role;

    @Column(name = "create_time", nullable = false)
    private LocalDateTime createTime;

    @Column(name = "update_time", nullable = false)
    private LocalDateTime updateTime;
    @Builder
    public User(String name, String email, String password, String phone, String role, LocalDateTime createTime, LocalDateTime updateTime) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.role = role;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }
}
