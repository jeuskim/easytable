package com.example.easytable.controller;

import com.example.easytable.dto.api.user.request.LoginRequest;
import com.example.easytable.dto.api.user.request.RegisterRequest;
import com.example.easytable.dto.api.user.request.UpdateRequest;
import com.example.easytable.entity.User;
import com.example.easytable.repository.UserRepository;
import com.example.easytable.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    UserService userService;

    @Autowired
    ObjectMapper mapper;


    @BeforeEach
    void clean() {
        userRepository.deleteAll();
    }


    @Test
    void 유저등록() throws Exception {

        RegisterRequest request = RegisterRequest.builder()
                .name("name")
                .email("test@test.com")
                .password("password!@")
                .passwordCheck("password!@")
                .phone("010-1234-5678")
                .build();

        String json = mapper.writeValueAsString(request);

        mockMvc.perform(post("/register")
                .content(json)
                .contentType(APPLICATION_JSON))
                .andExpect(jsonPath("returnCode").value("0000"))
                .andExpect(jsonPath("returnMessage").value("Success"))
                .andExpect(status().isOk())
                .andDo(print());

        Assertions.assertEquals(1L, userRepository.count());



    }


    @Test
    void 유저등록_valid() throws Exception {

        RegisterRequest request = RegisterRequest.builder()
                .name("name")
                .email("test@test.com")
                .password("password")
                .passwordCheck("password")
                .phone("010-1234-5678")
                .build();

        String json = mapper.writeValueAsString(request);

        mockMvc.perform(post("/register")
                        .content(json)
                        .contentType(APPLICATION_JSON))
                .andExpect(jsonPath("returnCode").value("0001"))
                .andExpect(jsonPath("returnMessage").value("잘못된 입력입니다."))
                .andExpect(status().isOk())
                .andDo(print());




    }


    @Test
    void 유저등록_비밀번호_다름() throws Exception {

        RegisterRequest request = RegisterRequest.builder()
                .name("test")
                .email("test@test.com")
                .password("password!@")
                .passwordCheck("password!@#")
                .phone("010-1234-5678")
                .build();

        String json = mapper.writeValueAsString(request);

        mockMvc.perform(post("/register")
                        .content(json)
                        .contentType(APPLICATION_JSON))
                .andExpect(jsonPath("returnCode").value("0001"))
                .andExpect(jsonPath("returnMessage").value("비밀번호와 비밀번호 확인이 다릅니다."))
                .andDo(print());

    }

    @Test
    void 로그인() throws Exception {

        User user = userRepository.save(User.builder()
                .name("name")
                .email("test@test.com")
                .password(encoder.encode("password!@"))
                .phone("010-1234-5678")
                .build());

        userRepository.save(user);

        LoginRequest request = new LoginRequest("test@test.com", "password!@");

        String json = mapper.writeValueAsString(request);

        mockMvc.perform(post("/login")
                        .content(json)
                        .contentType(APPLICATION_JSON))
                .andExpect(jsonPath("returnCode").value("0000"))
                .andExpect(jsonPath("returnMessage").value("Success"))
                .andExpect(status().isOk())
                .andDo(print());



    }

    @Test
    void 로그인_비밀번호_다름() throws Exception {

        User user = userRepository.save(User.builder()
                .name("name")
                .email("test@test.com")
                .password(encoder.encode("password!@"))
                .phone("010-1234-5678")
                .build());

        userRepository.save(user);

        LoginRequest request = new LoginRequest("test@test.com", "password!@#");

        String json = mapper.writeValueAsString(request);

        mockMvc.perform(post("/login")
                        .content(json)
                        .contentType(APPLICATION_JSON))
                .andExpect(jsonPath("returnCode").value("0001"))
                .andExpect(jsonPath("returnMessage").value("아이디나 패스워드가 다릅니다."))
                .andExpect(status().isOk())
                .andDo(print());



    }

    @Test
    void 비밀번호변경() throws Exception {


        User user = userRepository.save(User.builder()
                .name("name")
                .email("test@test.com")
                .password(encoder.encode("password!@"))
                .phone("010-1234-5678")
                .build());

        userRepository.save(user);

        UpdateRequest request = new UpdateRequest("password!@", "changePassword!@", "changePassword!@");

        String json = mapper.writeValueAsString(request);

        mockMvc.perform(post("/update")
                        .sessionAttr("userId", user.getId())
                        .content(json)
                        .contentType(APPLICATION_JSON))
                .andExpect(jsonPath("returnCode").value("0000"))
                .andExpect(jsonPath("returnMessage").value("Success"))
                .andExpect(status().isOk())
                .andDo(print());


    }

    @Test
    void 비밀번호변경_비밀번호_틀림() throws Exception {


        User user = userRepository.save(User.builder()
                .name("name")
                .email("test@test.com")
                .password(encoder.encode("password!@"))
                .phone("010-1234-5678")
                .build());

        userRepository.save(user);

        UpdateRequest request = new UpdateRequest("password!@1", "changePassword!@", "changePassword!@");

        String json = mapper.writeValueAsString(request);

        mockMvc.perform(post("/update")
                        .sessionAttr("userId", user.getId())
                        .content(json)
                        .contentType(APPLICATION_JSON))
                .andExpect(jsonPath("returnCode").value("0001"))
                .andExpect(jsonPath("returnMessage").value("현재 비밀번호가 다릅니다."))
                .andExpect(status().isOk())
                .andDo(print());


    }

    @Test
    void 비밀번호변경_비밀번호확인_다름() throws Exception {


        User user = userRepository.save(User.builder()
                .name("name")
                .email("test@test.com")
                .password(encoder.encode("password!@"))
                .phone("010-1234-5678")
                .build());

        userRepository.save(user);

        UpdateRequest request = new UpdateRequest("password!@1", "changePassword!@", "changePassword!@#");

        String json = mapper.writeValueAsString(request);

        mockMvc.perform(post("/update")
                        .sessionAttr("userId", user.getId())
                        .content(json)
                        .contentType(APPLICATION_JSON))
                .andExpect(jsonPath("returnCode").value("0001"))
                .andExpect(jsonPath("returnMessage").value("비밀번호와 비밀번호 확인이 다릅니다."))
                .andExpect(status().isOk())
                .andDo(print());


    }


}