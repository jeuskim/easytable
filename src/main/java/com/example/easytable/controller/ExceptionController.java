package com.example.easytable.controller;

import com.example.easytable.dto.api.response.ApiResponse;
import com.example.easytable.exception.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public ApiResponse AuthException(UnauthorizedException e) {

        return ApiResponse.fail("권한이 없습니다.");
    }


    @ExceptionHandler
    public ApiResponse validException(MethodArgumentNotValidException e) {

        return ApiResponse.fail("잘못된 입력입니다.");
    }

    @ExceptionHandler
    public ApiResponse runtimeException(RuntimeException e) {

        return ApiResponse.fail(e.getMessage());
    }

}
