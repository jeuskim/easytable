package com.example.easytable.exception;

import lombok.Getter;

@Getter
public class RestaurantNotFoundException extends EasyTableException {

    private static final String MESSAGE = "가게를 찾을 수 없습니다.";

    public RestaurantNotFoundException() {
        super(MESSAGE);
    }


    @Override
    public String getStatusCode() {
        return "404";
    }
}
