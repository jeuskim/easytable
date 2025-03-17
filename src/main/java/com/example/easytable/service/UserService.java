package com.example.easytable.service;

import com.example.easytable.dto.api.user.request.LoginRequest;
import com.example.easytable.dto.api.user.request.RegisterRequest;
import com.example.easytable.dto.api.user.request.UpdateRequest;
import com.example.easytable.dto.service.request.LoginParam;
import com.example.easytable.dto.service.request.RegisterParam;
import com.example.easytable.dto.service.request.UpdateParam;
import com.example.easytable.entity.User;
import com.example.easytable.repository.UserRepository;
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

    public void registerUser(RegisterParam param) {

        if (!param.getPassword().equals(param.getPasswordCheck())) {
            throw new RuntimeException("비밀번호와 비밀번호 확인이 다릅니다.");
        }

        String encode = passwordEncoder.encode(param.getPassword());

        userRepository.save(
                User.builder()
                        .name(param.getName())
                        .email(param.getEmail())
                        .password(encode)
                        .phone(param.getPhone())
                        .role("GUEST")
                        .build()
        );


    }

    public Long login(LoginParam param) {

        User user = userRepository.findUserByEmail(param.getEmail())
                .orElseThrow(() -> new RuntimeException("해당 유저가 없습니다."));

        if (!passwordEncoder.matches(param.getPassword(), user.getPassword())) {
            throw new RuntimeException("아이디나 패스워드가 다릅니다.");
        }

        return user.getId();


    }

    public void updateUserProfile(User user, UpdateParam param) {

        User changeUser = userRepository.findById(user.getId()).get();

        if (!param.getChangePassword().equals(param.getPasswordCheck())) {
            throw new RuntimeException("비밀번호와 비밀번호 확인이 다릅니다.");
        }

        if (!passwordEncoder.matches(param.getNowPassword(), user.getPassword())) {

            throw new RuntimeException("현재 비밀번호가 다릅니다.");
        }

        changeUser.changePassword(passwordEncoder.encode(param.getChangePassword()));

    }

}
