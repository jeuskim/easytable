package com.example.easytable.controller;

import com.example.easytable.dto.api.response.ApiResponse;
import com.example.easytable.dto.api.user.request.LoginRequest;
import com.example.easytable.dto.api.user.request.RegisterRequest;
import com.example.easytable.dto.api.user.request.UpdateRequest;
import com.example.easytable.entity.User;
import com.example.easytable.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ApiResponse registerUser(@Valid @RequestBody RegisterRequest request) {

        log.info("허허 = {}", request);

        userService.registerUser(request.convert());

        return ApiResponse.success(null);

    }

    @PostMapping("/login")
    public ApiResponse login(@Valid @RequestBody LoginRequest loginRequest, HttpServletRequest request) {

        Long userId = userService.login(loginRequest.convert());

        HttpSession session = request.getSession();

        session.setAttribute("userId", userId);

        return ApiResponse.success(null);

    }

    @PostMapping("/update")
    public ApiResponse updateUserProfile(User user, @Valid @RequestBody UpdateRequest request) {

        userService.updateUserProfile(user, request.convert());

        return ApiResponse.success(null);

    }


}
