package com.example.easytable.dto.service.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CreateReservationParam {

    private Long restaurantId;
    private LocalDateTime reservationDatetime;
    private Integer peopleNumber;


}
