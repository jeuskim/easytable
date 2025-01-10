package com.example.easytable.dto.request.reservation;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateReservationRequest {

    private Integer userId;
    private Integer restaurantId;
    private LocalDateTime reservationDatetime;
    private Integer peopleNumber;


}
