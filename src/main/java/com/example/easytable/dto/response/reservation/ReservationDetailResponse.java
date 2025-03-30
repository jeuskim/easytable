package com.example.easytable.dto.response.reservation;

import com.example.easytable.entity.Reservation;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReservationDetailResponse {

    private String name;
    private String restaurantName;
    private LocalDateTime reservationDatetime;
    private String status;


    public ReservationDetailResponse(Reservation reservation) {

        this.name = reservation.getUser().getName();
        this.restaurantName = reservation.getRestaurant().getName();
        this.reservationDatetime = reservation.getReservationDatetime();
        this.status = reservation.getStatus();
    }
}
