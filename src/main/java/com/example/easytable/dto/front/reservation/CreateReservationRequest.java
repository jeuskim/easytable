package com.example.easytable.dto.front.reservation;

import com.example.easytable.dto.service.request.CreateReservationParam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateReservationRequest {

    private Long restaurantId;
    private LocalDateTime reservationDatetime;
    private Integer peopleNumber;


    public CreateReservationParam convert() {
        return new CreateReservationParam(restaurantId, reservationDatetime, peopleNumber);
    }



}
