package com.example.easytable.service;

import com.example.easytable.dto.api.request.review.EditReviewRequest;
import com.example.easytable.dto.api.request.review.WriteReviewRequest;
import com.example.easytable.dto.api.response.ReviewListResponse;
import com.example.easytable.entity.Restaurant;
import com.example.easytable.entity.Review;
import com.example.easytable.entity.User;
import com.example.easytable.repository.RestaurantRepository;
import com.example.easytable.repository.UserRepository;
import com.example.easytable.repository.review.ReviewRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReviewServiceTest {


    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    ReviewService reviewService;


    @BeforeEach
    void clean() {
        reviewRepository.deleteAll();
        restaurantRepository.deleteAll();
        userRepository.deleteAll();
    }


    @Test
    void 리뷰쓰기() {

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

        WriteReviewRequest request = WriteReviewRequest.builder()
                .restaurantId(restaurant.getId())
                .rating(3)
                .comment("comment")
                .build();

        reviewService.writeReview(user, request.convert());

        Assertions.assertEquals(1L, reviewRepository.count());

    }

    @Test
    void 리뷰삭제() {

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

        reviewService.deleteReview(user, review.getId());

        Assertions.assertEquals(0, reviewRepository.count());

    }


    @Test
    void 리뷰수정() {

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

        reviewService.editReview(user, review.getId(), request.convert());

        Review changeReview = reviewRepository.findById(review.getId()).get();


        Assertions.assertEquals("change", changeReview.getComment());
        Assertions.assertEquals(5, changeReview.getRating());

    }

    @Test
    void 리뷰리스트() {

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

        List<ReviewListResponse> responses = reviewService.getReviews(restaurant.getId());

        Assertions.assertEquals(100L, responses.size());
        Assertions.assertEquals("comment1", responses.get(0).getComment());


    }
    @Test
    void 평점() {

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


        Assertions.assertEquals(3.0, reviewService.getAverageRating(restaurant.getId()));

    }





}