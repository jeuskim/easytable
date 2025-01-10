package com.example.easytable.exception;

import lombok.Getter;

@Getter
public class UserNotFoundException extends EasyTableException {

    private static final String MESSAGE = "유저를 찾을 수 없습니다.";

    public UserNotFoundException() {
        super(MESSAGE);
    }



    @Override
    public String getStatusCode() {
        return "404";
    }
}
