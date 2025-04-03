package com.example.easytable.exception;

public class RestaurantNotFoundException extends EasyTableException {
    private static final String MESSAGE = "해당 가게를 찾을 수 없습니다.";


    public RestaurantNotFoundException() {
        super(MESSAGE);
    }

    public RestaurantNotFoundException(Throwable cause) {
        super(MESSAGE, cause);
    }


    @Override
    public String getStatusCode() {
        return "0003";
    }

}
