package com.example.easytable.entity;

import com.example.easytable.entity.base.BaseEntity;
import lombok.*;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import static lombok.AccessLevel.*;

@Entity
@Getter
@Table(name = "users")
@ToString
@NoArgsConstructor(access = PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String password;
    private String phone;
    private String role;


    @Builder
    public User(String name, String email, String password, String phone, String role, LocalDateTime createTime, LocalDateTime updateTime) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.role = role;
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }
}
