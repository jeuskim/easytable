package com.example.easytable.dto.response.reservation;

import com.example.easytable.dto.response.restaurant.RestaurantListResponse;
import com.example.easytable.entity.Reservation;
import com.example.easytable.entity.Restaurant;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReservationListResponse {

    private int reservationId;
    private RestaurantListResponse restaurant;
    private String status;
    private int peopleNumber;
    private LocalDateTime reservationDatetime;



    public ReservationListResponse(Reservation reservation) {
        this.reservationId = reservation.getReservationId();
        this.restaurant = new RestaurantListResponse(reservation.getRestaurant());
        this.status = reservation.getStatus();
        this.peopleNumber = reservation.getPeopleNumber();
        this.reservationDatetime = reservation.getReservationDatetime();
    }
}
