package com.example.easytable.exception;

public class UserNotFoundException extends EasyTableException {
    private static final String MESSAGE = "해당 유저를 찾을 수 없습니다.";


    public UserNotFoundException() {
        super(MESSAGE);
    }

    public UserNotFoundException(Throwable cause) {
        super(MESSAGE, cause);
    }


    @Override
    public String getStatusCode() {
        return "0002";
    }

}
