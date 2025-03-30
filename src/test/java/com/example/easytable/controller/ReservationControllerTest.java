package com.example.easytable.controller;

import com.example.easytable.dto.front.reservation.CreateReservationRequest;
import com.example.easytable.dto.response.reservation.ReservationDetailResponse;
import com.example.easytable.dto.response.reservation.ReservationListResponse;
import com.example.easytable.dto.service.request.CreateReservationParam;
import com.example.easytable.entity.Reservation;
import com.example.easytable.entity.Restaurant;
import com.example.easytable.entity.User;
import com.example.easytable.repository.ReservationRepository;
import com.example.easytable.repository.RestaurantRepository;
import com.example.easytable.repository.UserRepository;
import com.example.easytable.service.ReservationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ReservationControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

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
    void 예약생성() throws Exception {

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

        CreateReservationRequest request = new CreateReservationRequest(restaurant.getId(), LocalDateTime.now(), 3);

        String json = mapper.writeValueAsString(request);
        mockMvc.perform(post("/reservations/create")
                        .sessionAttr("userId", user.getId())
                        .content(json)
                        .contentType(APPLICATION_JSON))
                .andExpect(jsonPath("returnCode").value("0000"))
                .andExpect(jsonPath("returnMessage").value("Success"))
                .andDo(print());

        Assertions.assertEquals(1L, reservationRepository.count());

    }

    @Test
    void 예약리스트() throws Exception {

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

        mockMvc.perform(get("/reservations")
                        .sessionAttr("userId", user.getId()))
                .andExpect(jsonPath("returnCode").value("0000"))
                .andExpect(jsonPath("returnMessage").value("Success"))
                .andExpect(jsonPath("data.size()").value(10L))
                .andDo(print());


    }

    @Test
    void 예약정보() throws Exception {

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

        mockMvc.perform(get("/reservations/{reservationId}",reservation.getId())
                        .sessionAttr("userId", user.getId()))
                .andExpect(jsonPath("returnCode").value("0000"))
                .andExpect(jsonPath("returnMessage").value("Success"))
                .andExpect(jsonPath("data.name").value("test2"))
                .andExpect(jsonPath("data.restaurantName").value("test restaurant"))
                .andExpect(jsonPath("data.status").value("PENDING"))
                .andDo(print());


    }


    @Test
    void 예약취소() throws Exception {

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

        mockMvc.perform(patch("/reservations/{reservationId}", reservation.getId())
                        .sessionAttr("userId", user.getId()))
                .andExpect(jsonPath("returnCode").value("0000"))
                .andExpect(jsonPath("returnMessage").value("Success"))
                .andDo(print());


        Reservation change = reservationRepository.findById(reservation.getId()).get();
        Assertions.assertEquals("CANCEL", change.getStatus());





    }



}