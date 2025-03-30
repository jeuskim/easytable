package com.example.easytable.controller;

import com.example.easytable.dto.front.reservation.CreateReservationRequest;
import com.example.easytable.dto.response.ApiResponse;
import com.example.easytable.dto.response.reservation.ReservationDetailResponse;
import com.example.easytable.dto.response.reservation.ReservationListResponse;
import com.example.easytable.entity.User;
import com.example.easytable.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;


    @PostMapping("/reservations/create")
    public ApiResponse createReservation(User user, @RequestBody CreateReservationRequest request) {

        reservationService.createReservation(user,request.convert());

        return ApiResponse.success(null);


    }
    @GetMapping("/reservations")
    public ApiResponse<List<ReservationListResponse>> getReservations(User user) {

        return ApiResponse.success(reservationService.getReservations(user));
    }

    @GetMapping("/reservations/{reservationId}")
    public ApiResponse<ReservationDetailResponse> getReservation(User user,@PathVariable Long reservationId) {

        return ApiResponse.success(reservationService.getReservation(user, reservationId));

    }

    @PatchMapping("/reservations/{reservationId}")
    public ApiResponse cancelReservation(User user,@PathVariable Long reservationId) {

        reservationService.cancelReservation(user, reservationId);

        return ApiResponse.success(null);
    }


}
