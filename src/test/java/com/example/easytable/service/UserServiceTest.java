package com.example.easytable.service;

import com.example.easytable.dto.api.user.request.LoginRequest;
import com.example.easytable.dto.api.user.request.RegisterRequest;
import com.example.easytable.dto.api.user.request.UpdateRequest;
import com.example.easytable.dto.service.request.LoginParam;
import com.example.easytable.dto.service.request.RegisterParam;
import com.example.easytable.dto.service.request.UpdateParam;
import com.example.easytable.entity.User;
import com.example.easytable.repository.UserRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder encoder;

    @BeforeEach
    void clean() {
        userRepository.deleteAll();
    }

    @Test
    void 등록() {

        RegisterRequest request = RegisterRequest.builder()
                .name("name")
                .email("test@test.com")
                .password("password")
                .passwordCheck("password")
                .phone("010-1234-5678")
                .build();

        RegisterParam param = request.convert();

        userService.registerUser(param);

        Assertions.assertEquals(1L, userRepository.count());

    }

    @Test
    void 로그인() {

        User user = userRepository.save(User.builder()
                .name("name")
                .email("test@test.com")
                .password(encoder.encode("password"))
                .phone("010-1234-5678")
                .build());

        LoginRequest request = new LoginRequest("test@test.com", "password");

        LoginParam param = request.convert();

        Long id = userService.login(param);

        Assertions.assertEquals(user.getId(), id);
    }

    @Test
    void 로그인_비밀번호_다름() {

        userRepository.save(User.builder()
                .name("name")
                .email("test@test.com")
                .password(encoder.encode("password"))
                .phone("010-1234-5678")
                .build());

        LoginRequest request = new LoginRequest("test@test.com", "password1111");

        LoginParam param = request.convert();


        Assertions.assertThrows(RuntimeException.class, () -> userService.login(param));
    }


    @Test
    void 회원정보_수정() {

        User user = userRepository.save(User.builder()
                .name("name")
                .email("test@test.com")
                .password(encoder.encode("password"))
                .phone("010-1234-5678")
                .build());

        UpdateRequest request = new UpdateRequest("password", "changePassword", "changePassword");
        UpdateParam param = request.convert();

        userService.updateUserProfile(user, param);

        User changeUser = userRepository.findById(user.getId()).get();

        Assertions.assertTrue(encoder.matches("changePassword", changeUser.getPassword()));


    }


    @Test
    void 회원정보_수정_비밀번호다름() {

        User user = userRepository.save(User.builder()
                .name("name")
                .email("test@test.com")
                .password(encoder.encode("password"))
                .phone("010-1234-5678")
                .build());

        UpdateRequest request = new UpdateRequest("nowPassword", "changePassword", "changePassword");
        UpdateParam param = request.convert();



        Assertions.assertThrows(RuntimeException.class,()->userService.updateUserProfile(user, param));


    }

    @Test
    void 회원정보_수정_비밀번호_확인_다름() {

        User user = userRepository.save(User.builder()
                .name("name")
                .email("test@test.com")
                .password(encoder.encode("password"))
                .phone("010-1234-5678")
                .build());

        UpdateRequest request = new UpdateRequest("password", "changePassword", "changePassword1111");
        UpdateParam param = request.convert();



        Assertions.assertThrows(RuntimeException.class,()->userService.updateUserProfile(user, param));


    }



}