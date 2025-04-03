package com.example.easytable.service;


import com.example.easytable.dto.service.request.RestaurantModifyParam;
import com.example.easytable.dto.service.request.RestaurantRegisterParam;
import com.example.easytable.entity.Restaurant;
import com.example.easytable.entity.Review;
import com.example.easytable.entity.user.User;
import com.example.easytable.entity.user.UserType;
import com.example.easytable.exception.RestaurantNotFoundException;
import com.example.easytable.exception.UnauthorizedException;
import com.example.easytable.repository.ReviewRepository;
import com.example.easytable.repository.UserRepository;
import com.example.easytable.repository.restaurant.RestaurantRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Slice;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static com.example.easytable.entity.user.UserType.*;

@SpringBootTest
class RestaurantServiceTest {

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    ReviewRepository reviewRepository;

    @BeforeEach
    void clean(){
        reviewRepository.deleteAll();
        restaurantRepository.deleteAll();
        userRepository.deleteAll();
    }


    @Test
    void 가게_등록() {

        User manger = User.builder()
                .name("test")
                .email("test@test.com")
                .password("test")
                .phone("010-1234-5678")
                .userType(MANAGER)
                .build();

        userRepository.save(manger);

        RestaurantRegisterParam param = RestaurantRegisterParam.builder()
                .name("test restaurant")
                .location("test location")
                .openingHours("12:00")
                .closingHours("21:00")
                .cuisineType("cuisineType")
                .description("test description")
                .build();


        restaurantService.registerRestaurant(manger, param);
        List<Restaurant> restaurants = restaurantRepository.findAll();

        Assertions.assertEquals(1L, restaurantRepository.count());
        Assertions.assertEquals("test restaurant",restaurants.get(0).getName());
        Assertions.assertEquals("test location", restaurants.get(0).getLocation());
        Assertions.assertEquals("12:00", restaurants.get(0).getOpeningHours());
        Assertions.assertEquals("21:00",restaurants.get(0).getClosingHours());
        Assertions.assertEquals( "cuisineType",restaurants.get(0).getCuisineType());
        Assertions.assertEquals("test description", restaurants.get(0).getDescription());


    }

    @Test
    void 가게_등록_권한없음() {

        User user = User.builder()
                .name("test")
                .email("test@test.com")
                .password("test")
                .phone("010-1234-5678")
                .userType(USER)
                .build();

        userRepository.save(user);

        RestaurantRegisterParam param = RestaurantRegisterParam.builder()
                .name("test restaurant")
                .location("test location")
                .openingHours("12:00")
                .closingHours("21:00")
                .cuisineType("cuisineType")
                .description("test description")
                .build();



        Assertions.assertThrows(UnauthorizedException.class, () -> restaurantService.registerRestaurant(user, param));


    }

    @Test
    void 가게_수정() {

        User manger = User.builder()
                .name("test")
                .email("test@test.com")
                .password("test")
                .phone("010-1234-5678")
                .userType(MANAGER)
                .build();

        userRepository.save(manger);

        Restaurant restaurant = Restaurant.builder()
                .name("test restaurant")
                .location("test location")
                .openingHours("12:00")
                .closingHours("21:00")
                .cuisineType("cuisineType")
                .description("test description")
                .manager(manger)
                .build();

        restaurantRepository.save(restaurant);

        RestaurantModifyParam param = RestaurantModifyParam.builder()
                .name("modify restaurant")
                .location("modify location")
                .openingHours("13:00")
                .closingHours("22:00")
                .cuisineType("modify cuisineType")
                .description("modify description")
                .build();

        restaurantService.modifyRestaurant(manger, restaurant.getId(), param);

        Restaurant modifyRestaurant = restaurantRepository.findById(restaurant.getId()).get();
        Assertions.assertEquals(1L, restaurantRepository.count());
        Assertions.assertEquals("modify restaurant",modifyRestaurant.getName());
        Assertions.assertEquals("modify location", modifyRestaurant.getLocation());
        Assertions.assertEquals("13:00", modifyRestaurant.getOpeningHours());
        Assertions.assertEquals("22:00",modifyRestaurant.getClosingHours());
        Assertions.assertEquals( "modify cuisineType",modifyRestaurant.getCuisineType());
        Assertions.assertEquals("modify description", modifyRestaurant.getDescription());

    }

    @Test
    void 가게_수정_권한없음() {

        User manger = User.builder()
                .name("test")
                .email("test@test.com")
                .password("test")
                .phone("010-1234-5678")
                .userType(MANAGER)
                .build();
        User user = User.builder()
                .name("test2")
                .email("test2@test.com")
                .password("test2")
                .phone("010-1234-5678")
                .userType(USER)
                .build();

        userRepository.save(manger);
        userRepository.save(user);

        Restaurant restaurant = Restaurant.builder()
                .name("test restaurant")
                .location("test location")
                .openingHours("12:00")
                .closingHours("21:00")
                .cuisineType("cuisineType")
                .description("test description")
                .manager(manger)
                .build();

        restaurantRepository.save(restaurant);

        RestaurantModifyParam param = RestaurantModifyParam.builder()
                .name("modify restaurant")
                .location("modify location")
                .openingHours("13:00")
                .closingHours("22:00")
                .cuisineType("modify cuisineType")
                .description("modify description")
                .build();


        Assertions.assertThrows(UnauthorizedException.class, () -> restaurantService.modifyRestaurant(user, restaurant.getId(), param));

    }

    @Test
    void 가게_리스트() {

        List<Restaurant> restaurants = new ArrayList<>();

        User manger = User.builder()
                .name("test")
                .email("test@test.com")
                .password("test")
                .phone("010-1234-5678")
                .userType(MANAGER)
                .build();

        userRepository.save(manger);

        IntStream.rangeClosed(1, 100).forEach((i) -> restaurants.add(
                Restaurant.builder()
                        .name("restaurant" + i)
                        .location("location" + i)
                        .openingHours("12:00")
                        .closingHours("21:00")
                        .cuisineType("cuisineType")
                        .description("description" + i)
                        .manager(manger)
                        .build())
        );

        restaurantRepository.saveAll(restaurants);


        Slice<Restaurant> slice = restaurantService.getRestaurants("restaurant", 20);

        Assertions.assertFalse(slice.hasNext());
        Assertions.assertEquals(5, slice.getContent().size());

    }

    @Test
    void 가게_리스트2() {

        List<Restaurant> restaurants = new ArrayList<>();

        User manger = User.builder()
                .name("test")
                .email("test@test.com")
                .password("test")
                .phone("010-1234-5678")
                .userType(MANAGER)
                .build();

        userRepository.save(manger);

        IntStream.rangeClosed(1, 100).forEach((i) -> restaurants.add(
                Restaurant.builder()
                        .name("restaurant" + i)
                        .location("location" + i)
                        .openingHours("12:00")
                        .closingHours("21:00")
                        .cuisineType("cuisineType")
                        .description("description" + i)
                        .manager(manger)
                        .build())
        );

        restaurantRepository.saveAll(restaurants);


        Slice<Restaurant> slice = restaurantService.getRestaurants("restaurant", 19);

        Assertions.assertTrue(slice.hasNext());
        Assertions.assertEquals(5, slice.getContent().size());

    }

    @Test
    void 리뷰_점수() {

        User manger = User.builder()
                .name("test")
                .email("test@test.com")
                .password("test")
                .phone("010-1234-5678")
                .userType(MANAGER)
                .build();
        User user = User.builder()
                .name("test2")
                .email("test2@test.com")
                .password("test2")
                .phone("010-1234-5678")
                .userType(USER)
                .build();

        userRepository.save(manger);
        userRepository.save(user);


        Restaurant restaurant = Restaurant.builder()
                .name("test restaurant")
                .location("test location")
                .openingHours("12:00")
                .closingHours("21:00")
                .cuisineType("cuisineType")
                .description("test description")
                .manager(manger)
                .build();

        restaurantRepository.save(restaurant);

        List<Review> reviews = new ArrayList<>();

        IntStream.rangeClosed(1, 10).forEach((i) -> reviews.add(
                Review.builder()
                        .user(user)
                        .restaurant(restaurant)
                        .rating(i)
                        .comment("comment" + i)
                        .build())
        );

        reviewRepository.saveAll(reviews);


        Assertions.assertEquals(5.5, restaurantService.getRating(restaurant.getId()));




    }
}