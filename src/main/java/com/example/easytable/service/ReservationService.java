package com.example.easytable.service;

import com.example.easytable.dto.front.reservation.CreateReservationRequest;
import com.example.easytable.dto.response.reservation.ReservationDetailResponse;
import com.example.easytable.dto.response.reservation.ReservationListResponse;
import com.example.easytable.dto.service.request.CreateReservationParam;
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


    public void createReservation(User user,CreateReservationParam param) {

        Restaurant restaurant = restaurantRepository.findById(param.getRestaurantId())
                .orElseThrow(RestaurantNotFoundException::new);


        reservationRepository.save(
                Reservation.builder()
                .user(user)
                .restaurant(restaurant)
                .peopleNumber(param.getPeopleNumber())
                .status("PENDING")
                .reservationDatetime(param.getReservationDatetime())
                .build());

    }

    public List<ReservationListResponse> getReservations(User user) {

        return reservationRepository.findAllByUser(user).stream().map(ReservationListResponse::new).toList();


    }

    public ReservationDetailResponse getReservation(User user,Long reservationId) {

        Reservation reservation = reservationRepository.findWithUserAndRestaurantById(reservationId)
                .orElseThrow(ReservationNotFoundException::new);

        authCheck(user, reservation);

        return new ReservationDetailResponse(reservation);




    }


    public void cancelReservation(User user, Long reservationId) {

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(UserNotFoundException::new);

        authCheck(user, reservation);

        reservation.cancel();


    }

    private static void authCheck(User user, Reservation reservation) {
        if (!reservation.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("권한이 없습니다.");
        }
    }

}
