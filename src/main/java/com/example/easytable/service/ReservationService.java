package com.example.easytable.service;

import com.example.easytable.dto.request.reservation.CreateReservationRequest;
import com.example.easytable.dto.response.reservation.ReservationDetailResponse;
import com.example.easytable.dto.response.reservation.ReservationListResponse;
import com.example.easytable.entity.Reservation;
import com.example.easytable.entity.Restaurant;
import com.example.easytable.entity.User;
import com.example.easytable.exception.ReservationNotFoundException;
import com.example.easytable.exception.RestaurantNotFoundException;
import com.example.easytable.exception.UserNotFoundException;
import com.example.easytable.repository.ReservationRepository;
import com.example.easytable.repository.RestaurantRepository;
import com.example.easytable.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;


    public void createReservation(CreateReservationRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(UserNotFoundException::new);

        Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId())
                .orElseThrow(RestaurantNotFoundException::new);


        reservationRepository.save(
                Reservation.builder()
                .user(user)
                .restaurant(restaurant)
                .peopleNumber(request.getPeopleNumber())
                .status("PENDING")
                .reservationDatetime(request.getReservationDatetime())
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build());

    }

    public List<ReservationListResponse> getReservations(Integer userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        return reservationRepository.findAllByUser(user).stream().map(ReservationListResponse::new).toList();


    }

    public ReservationDetailResponse getReservation(Integer reservationId) {

        Reservation reservation = reservationRepository.findWithUserAndRestaurantById(reservationId)
                .orElseThrow(ReservationNotFoundException::new);

        return new ReservationDetailResponse(reservation);




    }

    public void cancelReservation(Integer reservationId) {

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(UserNotFoundException::new);

        reservation.cancel();


    }

}
