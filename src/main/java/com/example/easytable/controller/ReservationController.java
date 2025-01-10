package com.example.easytable.controller;

import com.example.easytable.dto.request.reservation.CreateReservationRequest;
import com.example.easytable.dto.response.reservation.ReservationDetailResponse;
import com.example.easytable.dto.response.reservation.ReservationListResponse;
import com.example.easytable.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;


    @PostMapping("/reservations/create")
    public void createReservation(CreateReservationRequest request) {

        reservationService.createReservation(request);


    }
    @GetMapping("/reservations")
    public List<ReservationListResponse> getReservations(Integer userId) {

        return reservationService.getReservations(userId);
    }

    @GetMapping("/reservations/{reservationId}")
    public ReservationDetailResponse getReservation(@PathVariable Integer reservationId) {

        return reservationService.getReservation(reservationId);

    }

    @PatchMapping("/reservations/{reservationId}")
    public void cancelReservation(@PathVariable Integer reservationId) {

        reservationService.cancelReservation(reservationId);
    }


}
