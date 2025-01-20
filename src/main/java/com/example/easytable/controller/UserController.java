package com.example.easytable.controller;

import com.example.easytable.dto.user.request.LoginRequest;
import com.example.easytable.dto.user.request.RegisterRequest;
import com.example.easytable.dto.user.request.UpdateRequest;
import com.example.easytable.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    public void registerUser(@Valid @RequestBody RegisterRequest request) {

        userService.registerUser(request);


    }

    public void login(@Valid @RequestBody LoginRequest loginRequest, HttpServletRequest request) {

        Integer userId = userService.login(loginRequest);

        HttpSession session = request.getSession();

        session.setAttribute("userId", userId);


    }

    public void updateUserProfile(@Valid @RequestBody UpdateRequest request, @SessionAttribute("userId") Integer userId) {

        userService.updateUserProfile(userId, request);

    }


}
