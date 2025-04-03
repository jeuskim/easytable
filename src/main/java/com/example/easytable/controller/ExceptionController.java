package com.example.easytable.controller;

import com.example.easytable.dto.front.response.ApiResponse;
import com.example.easytable.exception.EasyTableException;
import com.example.easytable.exception.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler
    public ApiResponse CookieException(EasyTableException e) {

        return ApiResponse.fail(e.getStatusCode(),e.getMessage());
    }
}
