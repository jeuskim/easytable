package com.example.easytable.controller;

import com.example.easytable.dto.api.request.review.EditReviewRequest;
import com.example.easytable.dto.api.request.review.WriteReviewRequest;
import com.example.easytable.entity.Restaurant;
import com.example.easytable.entity.Review;
import com.example.easytable.entity.User;
import com.example.easytable.repository.RestaurantRepository;
import com.example.easytable.repository.UserRepository;
import com.example.easytable.repository.review.ReviewRepository;
import com.example.easytable.service.ReviewService;
import com.fasterxml.jackson.core.JsonProcessingException;
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
class ReviewControllerTest {


    @Autowired
    MockMvc mockMvc;

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    ReviewService reviewService;

    @Autowired
    ObjectMapper mapper;


    @BeforeEach
    void clean() {
        reviewRepository.deleteAll();
        restaurantRepository.deleteAll();
        userRepository.deleteAll();
    }


    @Test
    void 리뷰쓰기() throws Exception {

        User manager = User.builder()
                .name("manager")
                .email("manager@test.com")
                .phone("010-1234-5678")
                .role("MANAGER")
                .password("password")
                .build();

        userRepository.save(manager);

        Restaurant restaurant = Restaurant.builder()
                .name("test restaurant")
                .location("location")
                .cuisineType("cuisineType")
                .openingHours("09:00")
                .closingHours("21:00")
                .manager(manager)
                .build();

        restaurantRepository.save(restaurant);

        WriteReviewRequest request = WriteReviewRequest.builder()
                .restaurantId(restaurant.getId())
                .rating(3)
                .comment("comment")
                .build();

        String json = mapper.writeValueAsString(request);

        mockMvc.perform(post("/reviews/write")
                        .sessionAttr("userId", manager.getId())
                        .content(json)
                        .contentType(APPLICATION_JSON))
                .andExpect(jsonPath("returnCode").value("0000"))
                .andExpect(jsonPath("returnMessage").value("Success"))
                .andDo(print());

    }

    @Test
    void 리뷰쓰기_세션없음() throws Exception {

        User manager = User.builder()
                .name("manager")
                .email("manager@test.com")
                .phone("010-1234-5678")
                .role("MANAGER")
                .password("password")
                .build();

        userRepository.save(manager);

        Restaurant restaurant = Restaurant.builder()
                .name("test restaurant")
                .location("location")
                .cuisineType("cuisineType")
                .openingHours("09:00")
                .closingHours("21:00")
                .manager(manager)
                .build();

        restaurantRepository.save(restaurant);

        WriteReviewRequest request = WriteReviewRequest.builder()
                .restaurantId(restaurant.getId())
                .rating(3)
                .comment("comment")
                .build();

        String json = mapper.writeValueAsString(request);

        mockMvc.perform(post("/reviews/write")
                        .content(json)
                        .contentType(APPLICATION_JSON))
                .andExpect(jsonPath("returnCode").value("0001"))
                .andExpect(jsonPath("returnMessage").value("권한이 없습니다."))
                .andDo(print());

    }

    @Test
    void 리뷰삭제() throws Exception {

        User manager = User.builder()
                .name("manager")
                .email("manager@test.com")
                .phone("010-1234-5678")
                .role("MANAGER")
                .password("password")
                .build();

        User user = User.builder()
                .name("user")
                .email("user@test.com")
                .phone("010-1234-5678")
                .role("GUEST")
                .password("password")
                .build();

        userRepository.save(manager);
        userRepository.save(user);

        Restaurant restaurant = Restaurant.builder()
                .name("test restaurant")
                .location("location")
                .cuisineType("cuisineType")
                .openingHours("09:00")
                .closingHours("21:00")
                .manager(manager)
                .build();

        restaurantRepository.save(restaurant);

        Review review = Review.builder()
                .user(user)
                .restaurant(restaurant)
                .comment("comment")
                .rating(3)
                .build();

        reviewRepository.save(review);

        mockMvc.perform(delete("/reviews/{reviewId}",review.getId())
                        .sessionAttr("userId",user.getId()))
                .andExpect(jsonPath("returnCode").value("0000"))
                .andExpect(jsonPath("returnMessage").value("Success"))
                .andDo(print());


    }

    @Test
    void 리뷰삭제_세션없음() throws Exception {

        User manager = User.builder()
                .name("manager")
                .email("manager@test.com")
                .phone("010-1234-5678")
                .role("MANAGER")
                .password("password")
                .build();

        User user = User.builder()
                .name("user")
                .email("user@test.com")
                .phone("010-1234-5678")
                .role("GUEST")
                .password("password")
                .build();

        userRepository.save(manager);
        userRepository.save(user);

        Restaurant restaurant = Restaurant.builder()
                .name("test restaurant")
                .location("location")
                .cuisineType("cuisineType")
                .openingHours("09:00")
                .closingHours("21:00")
                .manager(manager)
                .build();

        restaurantRepository.save(restaurant);

        Review review = Review.builder()
                .user(user)
                .restaurant(restaurant)
                .comment("comment")
                .rating(3)
                .build();

        reviewRepository.save(review);

        mockMvc.perform(delete("/reviews/{reviewId}",review.getId()))
                .andExpect(jsonPath("returnCode").value("0001"))
                .andExpect(jsonPath("returnMessage").value("권한이 없습니다."))
                .andDo(print());


    }

    @Test
    void 리뷰삭제_다른유저() throws Exception {

        User manager = User.builder()
                .name("manager")
                .email("manager@test.com")
                .phone("010-1234-5678")
                .role("MANAGER")
                .password("password")
                .build();

        User user = User.builder()
                .name("user")
                .email("user@test.com")
                .phone("010-1234-5678")
                .role("GUEST")
                .password("password")
                .build();

        userRepository.save(manager);
        userRepository.save(user);

        Restaurant restaurant = Restaurant.builder()
                .name("test restaurant")
                .location("location")
                .cuisineType("cuisineType")
                .openingHours("09:00")
                .closingHours("21:00")
                .manager(manager)
                .build();

        restaurantRepository.save(restaurant);

        Review review = Review.builder()
                .user(user)
                .restaurant(restaurant)
                .comment("comment")
                .rating(3)
                .build();

        reviewRepository.save(review);

        mockMvc.perform(delete("/reviews/{reviewId}",review.getId())
                        .sessionAttr("userId",manager.getId()))
                .andExpect(jsonPath("returnCode").value("0001"))
                .andExpect(jsonPath("returnMessage").value("권한이 없습니다."))
                .andDo(print());


    }

    @Test
    void 리뷰수정() throws Exception {


        User manager = User.builder()
                .name("manager")
                .email("manager@test.com")
                .phone("010-1234-5678")
                .role("MANAGER")
                .password("password")
                .build();

        User user = User.builder()
                .name("user")
                .email("user@test.com")
                .phone("010-1234-5678")
                .role("GUEST")
                .password("password")
                .build();

        userRepository.save(manager);
        userRepository.save(user);

        Restaurant restaurant = Restaurant.builder()
                .name("test restaurant")
                .location("location")
                .cuisineType("cuisineType")
                .openingHours("09:00")
                .closingHours("21:00")
                .manager(manager)
                .build();

        restaurantRepository.save(restaurant);

        Review review = Review.builder()
                .user(user)
                .restaurant(restaurant)
                .comment("comment")
                .rating(3)
                .build();

        reviewRepository.save(review);

        EditReviewRequest request = new EditReviewRequest("change", 5);

        String json = mapper.writeValueAsString(request);
        mockMvc.perform(patch("/reviews/{reviewId}", review.getId())
                        .sessionAttr("userId", user.getId())
                        .content(json)
                        .contentType(APPLICATION_JSON))
                .andExpect(jsonPath("returnCode").value("0000"))
                .andExpect(jsonPath("returnMessage").value("Success"))
                .andDo(print());

        Review changeReview = reviewRepository.findById(review.getId()).get();

        Assertions.assertEquals("change", changeReview.getComment());
        Assertions.assertEquals(5, changeReview.getRating());

    }

    @Test
    void 리뷰수정_세션없음() throws Exception {


        User manager = User.builder()
                .name("manager")
                .email("manager@test.com")
                .phone("010-1234-5678")
                .role("MANAGER")
                .password("password")
                .build();

        User user = User.builder()
                .name("user")
                .email("user@test.com")
                .phone("010-1234-5678")
                .role("GUEST")
                .password("password")
                .build();

        userRepository.save(manager);
        userRepository.save(user);

        Restaurant restaurant = Restaurant.builder()
                .name("test restaurant")
                .location("location")
                .cuisineType("cuisineType")
                .openingHours("09:00")
                .closingHours("21:00")
                .manager(manager)
                .build();

        restaurantRepository.save(restaurant);

        Review review = Review.builder()
                .user(user)
                .restaurant(restaurant)
                .comment("comment")
                .rating(3)
                .build();

        reviewRepository.save(review);

        EditReviewRequest request = new EditReviewRequest("change", 5);

        String json = mapper.writeValueAsString(request);
        mockMvc.perform(patch("/reviews/{reviewId}", review.getId())
                        .content(json)
                        .contentType(APPLICATION_JSON))
                .andExpect(jsonPath("returnCode").value("0001"))
                .andExpect(jsonPath("returnMessage").value("권한이 없습니다."))
                .andDo(print());


    }

    @Test
    void 리뷰수정_다른유저() throws Exception {


        User manager = User.builder()
                .name("manager")
                .email("manager@test.com")
                .phone("010-1234-5678")
                .role("MANAGER")
                .password("password")
                .build();

        User user = User.builder()
                .name("user")
                .email("user@test.com")
                .phone("010-1234-5678")
                .role("GUEST")
                .password("password")
                .build();

        userRepository.save(manager);
        userRepository.save(user);

        Restaurant restaurant = Restaurant.builder()
                .name("test restaurant")
                .location("location")
                .cuisineType("cuisineType")
                .openingHours("09:00")
                .closingHours("21:00")
                .manager(manager)
                .build();

        restaurantRepository.save(restaurant);

        Review review = Review.builder()
                .user(user)
                .restaurant(restaurant)
                .comment("comment")
                .rating(3)
                .build();

        reviewRepository.save(review);

        EditReviewRequest request = new EditReviewRequest("change", 5);

        String json = mapper.writeValueAsString(request);
        mockMvc.perform(patch("/reviews/{reviewId}", review.getId())
                        .sessionAttr("userId",manager.getId())
                        .content(json)
                        .contentType(APPLICATION_JSON))
                .andExpect(jsonPath("returnCode").value("0001"))
                .andExpect(jsonPath("returnMessage").value("권한이 없습니다."))
                .andDo(print());

    }

    @Test
    void 리뷰리스트() throws Exception {


        User manager = User.builder()
                .name("manager")
                .email("manager@test.com")
                .phone("010-1234-5678")
                .role("MANAGER")
                .password("password")
                .build();

        User user = User.builder()
                .name("user")
                .email("user@test.com")
                .phone("010-1234-5678")
                .role("GUEST")
                .password("password")
                .build();

        userRepository.save(manager);
        userRepository.save(user);

        Restaurant restaurant = Restaurant.builder()
                .name("test restaurant")
                .location("location")
                .cuisineType("cuisineType")
                .openingHours("09:00")
                .closingHours("21:00")
                .manager(manager)
                .build();

        restaurantRepository.save(restaurant);

        List<Review> reviews = new ArrayList<>();

        IntStream.rangeClosed(1,100).forEach((i)->{
            reviews.add(Review.builder()
                    .user(user)
                    .restaurant(restaurant)
                    .comment("comment"+i)
                    .rating(3)
                    .build());
        });

        reviewRepository.saveAll(reviews);

        mockMvc.perform(get("/reviews/{restaurantId}", restaurant.getId()))
                .andExpect(jsonPath("returnCode").value("0000"))
                .andExpect(jsonPath("returnMessage").value("Success"))
                .andExpect(jsonPath("data.size()").value("100"))
                .andExpect(jsonPath("data[0].comment").value("comment1"))
                .andDo(print());



    }

    @Test
    void 평점() throws Exception {

        User manager = User.builder()
                .name("manager")
                .email("manager@test.com")
                .phone("010-1234-5678")
                .role("MANAGER")
                .password("password")
                .build();

        User user = User.builder()
                .name("user")
                .email("user@test.com")
                .phone("010-1234-5678")
                .role("GUEST")
                .password("password")
                .build();

        userRepository.save(manager);
        userRepository.save(user);

        Restaurant restaurant = Restaurant.builder()
                .name("test restaurant")
                .location("location")
                .cuisineType("cuisineType")
                .openingHours("09:00")
                .closingHours("21:00")
                .manager(manager)
                .build();

        restaurantRepository.save(restaurant);

        List<Review> reviews = new ArrayList<>();

        IntStream.rangeClosed(1,5).forEach((i)->{
            reviews.add(Review.builder()
                    .user(user)
                    .restaurant(restaurant)
                    .comment("comment"+i)
                    .rating(i)
                    .build());
        });

        reviewRepository.saveAll(reviews);

        mockMvc.perform(get("/reviews/{restaurantId}/rating", restaurant.getId()))
                .andExpect(jsonPath("returnCode").value("0000"))
                .andExpect(jsonPath("returnMessage").value("Success"))
                .andExpect(jsonPath("data").value(3.0))
                .andDo(print());


    }

}