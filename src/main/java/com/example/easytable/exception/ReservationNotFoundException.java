package com.example.easytable.exception;

import lombok.Getter;

@Getter
public class ReservationNotFoundException extends EasyTableException {

    private static final String MESSAGE = "예약을 찾을 수 없습니다.";

    public ReservationNotFoundException() {
        super(MESSAGE);
    }



    @Override
    public String getStatusCode() {
        return "404";
    }
}
