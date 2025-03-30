package com.example.easytable.service;

import com.example.easytable.dto.response.reservation.ReservationDetailResponse;
import com.example.easytable.dto.response.reservation.ReservationListResponse;
import com.example.easytable.dto.service.request.CreateReservationParam;
import com.example.easytable.entity.Reservation;
import com.example.easytable.entity.Restaurant;
import com.example.easytable.entity.User;
import com.example.easytable.repository.ReservationRepository;
import com.example.easytable.repository.RestaurantRepository;
import com.example.easytable.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReservationServiceTest {


    @Autowired
    UserRepository userRepository;

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    ReservationService reservationService;

    @BeforeEach
    void clean() {
        reservationRepository.deleteAll();
        restaurantRepository.deleteAll();
        userRepository.deleteAll();
    }


    @Test
    void 예약생성() {

        User manger = User.builder()
                .name("test")
                .email("test@test.com")
                .password("test")
                .phone("010-1234-5678")
                .role("MANAGER")
                .build();
        User user = User.builder()
                .name("test2")
                .email("test2@test.com")
                .password("test2")
                .phone("010-1234-5678")
                .role("USER")
                .build();

        userRepository.save(manger);
        userRepository.save(user);

        Restaurant restaurant = Restaurant.builder()
                .name("test restaurant")
                .location("test location")
                .openingHours("12:00")
                .closingHours("21:00")
                .cuisineType("cuisineType")
                .manager(manger)
                .build();

        restaurantRepository.save(restaurant);

        CreateReservationParam param = new CreateReservationParam(restaurant.getId(), LocalDateTime.now(), 3);
        reservationService.createReservation(user, param);

        Assertions.assertEquals(1L, reservationRepository.count());

    }

    @Test
    void 예약리스트() {

        User manger = User.builder()
                .name("test")
                .email("test@test.com")
                .password("test")
                .phone("010-1234-5678")
                .role("MANAGER")
                .build();
        User user = User.builder()
                .name("test2")
                .email("test2@test.com")
                .password("test2")
                .phone("010-1234-5678")
                .role("USER")
                .build();

        userRepository.save(manger);
        userRepository.save(user);

        Restaurant restaurant = Restaurant.builder()
                .name("test restaurant")
                .location("test location")
                .openingHours("12:00")
                .closingHours("21:00")
                .cuisineType("cuisineType")
                .manager(manger)
                .build();

        restaurantRepository.save(restaurant);

        List<Reservation> reservationList = new ArrayList<>();

        IntStream.rangeClosed(1,10).forEach((i)->{

            reservationList.add(Reservation.builder()
                    .user(user)
                    .reservationDatetime(LocalDateTime.now())
                    .status("PENDING")
                    .peopleNumber(i)
                    .restaurant(restaurant)
                    .build());
        });

        reservationRepository.saveAll(reservationList);

        List<ReservationListResponse> responses = reservationService.getReservations(user);

        Assertions.assertEquals(10L, responses.size());



    }

    @Test
    void 예약정보() {

        User manger = User.builder()
                .name("test")
                .email("test@test.com")
                .password("test")
                .phone("010-1234-5678")
                .role("MANAGER")
                .build();
        User user = User.builder()
                .name("test2")
                .email("test2@test.com")
                .password("test2")
                .phone("010-1234-5678")
                .role("USER")
                .build();

        userRepository.save(manger);
        userRepository.save(user);

        Restaurant restaurant = Restaurant.builder()
                .name("test restaurant")
                .location("test location")
                .openingHours("12:00")
                .closingHours("21:00")
                .cuisineType("cuisineType")
                .manager(manger)
                .build();

        restaurantRepository.save(restaurant);

        Reservation reservation = Reservation.builder()
                .user(user)
                .reservationDatetime(LocalDateTime.now())
                .status("PENDING")
                .peopleNumber(5)
                .restaurant(restaurant)
                .build();

        reservationRepository.save(reservation);

        ReservationDetailResponse response = reservationService.getReservation(user, reservation.getId());


        Assertions.assertEquals("test2", response.getName());
        Assertions.assertEquals("test restaurant", response.getRestaurantName());
        Assertions.assertEquals("PENDING", response.getStatus());


    }

    @Test
    void 예약취소() {

        User manger = User.builder()
                .name("test")
                .email("test@test.com")
                .password("test")
                .phone("010-1234-5678")
                .role("MANAGER")
                .build();
        User user = User.builder()
                .name("test2")
                .email("test2@test.com")
                .password("test2")
                .phone("010-1234-5678")
                .role("USER")
                .build();

        userRepository.save(manger);
        userRepository.save(user);

        Restaurant restaurant = Restaurant.builder()
                .name("test restaurant")
                .location("test location")
                .openingHours("12:00")
                .closingHours("21:00")
                .cuisineType("cuisineType")
                .manager(manger)
                .build();

        restaurantRepository.save(restaurant);

        Reservation reservation = Reservation.builder()
                .user(user)
                .reservationDatetime(LocalDateTime.now())
                .status("PENDING")
                .peopleNumber(5)
                .restaurant(restaurant)
                .build();

        reservationRepository.save(reservation);

        reservationService.cancelReservation(user, reservation.getId());

        Reservation change = reservationRepository.findById(reservation.getId()).get();
        Assertions.assertEquals("CANCEL", change.getStatus());





    }

}