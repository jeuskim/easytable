package com.example.easytable.service;

import com.example.easytable.dto.user.request.LoginRequest;
import com.example.easytable.dto.user.request.RegisterRequest;
import com.example.easytable.dto.user.request.UpdateRequest;
import com.example.easytable.entity.User;
import com.example.easytable.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void registerUser(RegisterRequest request) {

        if (!request.getPassword().equals(request.getPasswordCheck())) {
            throw new RuntimeException("비밀번호와 비밀번호 확인이 다릅니다.");
        }

        String encode = passwordEncoder.encode(request.getPassword());

        userRepository.save(
                User.builder()
                        .name(request.getName())
                        .email(request.getEmail())
                        .password(encode)
                        .phone(request.getPhone())
                        .role("GUEST")
                        .createTime(LocalDateTime.now())
                        .updateTime(LocalDateTime.now())
                        .build()
        );


    }

    public Integer login(LoginRequest request) {

        User user = userRepository.findUserByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("해당 유저가 없습니다."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("아이디나 패스워드가 다릅니다.");
        }

        return user.getUserId();


    }

    public void updateUserProfile(Integer userId, UpdateRequest request) {


        if (!request.getChangePassword().equals(request.getPasswordCheck())) {
            throw new RuntimeException("비밀번호와 비밀번호 확인이 다릅니다.");
        }


        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("해당 유저 없음."));

        if (passwordEncoder.matches(request.getNowPassword(), user.getPassword())) {

            user.changePassword(passwordEncoder.encode(request.getChangePassword()));

        }

    }

}
