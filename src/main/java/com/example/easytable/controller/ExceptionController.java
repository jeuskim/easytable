package com.example.easytable.controller;

import com.example.easytable.dto.front.response.ApiResponse;
import com.example.easytable.exception.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public ApiResponse CookieException(UnauthorizedException e) {

        return ApiResponse.fail("권한이 없습니다.");
    }
}
