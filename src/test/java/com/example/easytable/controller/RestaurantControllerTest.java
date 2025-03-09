package com.example.easytable.controller;

import com.example.easytable.dto.front.response.ListResponse;
import com.example.easytable.dto.front.response.RestaurantListResponse;
import com.example.easytable.dto.service.request.RestaurantListParam;
import com.example.easytable.dto.service.request.RestaurantModifyParam;
import com.example.easytable.dto.service.request.RestaurantRegisterParam;
import com.example.easytable.entity.Restaurant;
import com.example.easytable.entity.User;
import com.example.easytable.repository.UserRepository;
import com.example.easytable.repository.restaurant.RestaurantRepository;
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
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.StatusResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RestaurantControllerTest {


    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RestaurantRepository restaurantRepository;


    @BeforeEach
    void clean() {
        restaurantRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void 가게_등록() throws Exception {

        User manger = User.builder()
                .name("test")
                .email("test@test.com")
                .password("test")
                .phone("010-1234-5678")
                .role("MANAGER")
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

        String json = mapper.writeValueAsString(param);

        mockMvc.perform(post("/restaurants/register")
                        .sessionAttr("userId", manger.getId())
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.returnCode").value("0000"))
                .andExpect(jsonPath("$.returnMessage").value("Success"))
                .andDo(print());

        List<Restaurant> restaurants = restaurantRepository.findAll();

        Assertions.assertEquals(1L, restaurants.size());


    }

    @Test
    void 가게_등록_비로그인() throws Exception {

        User manger = User.builder()
                .name("test")
                .email("test@test.com")
                .password("test")
                .phone("010-1234-5678")
                .role("MANAGER")
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

        String json = mapper.writeValueAsString(param);

        mockMvc.perform(post("/restaurants/register")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.returnCode").value("0001"))
                .andExpect(jsonPath("$.returnMessage").value("권한이 없습니다."))
                .andDo(print());



    }

    @Test
    void 가게_수정() throws Exception {

        User manger = User.builder()
                .name("test")
                .email("test@test.com")
                .password("test")
                .phone("010-1234-5678")
                .role("MANAGER")
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

        String json = mapper.writeValueAsString(param);

        mockMvc.perform(patch("/restaurants/{restaurantId}", restaurant.getId())
                        .sessionAttr("userId", manger.getId())
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.returnCode").value("0000"))
                .andExpect(jsonPath("$.returnMessage").value("Success"))
                .andDo(print());

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
    void 가게_수정_다른유저() throws Exception {

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
                .password("test")
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

        String json = mapper.writeValueAsString(param);

        mockMvc.perform(patch("/restaurants/{restaurantId}", restaurant.getId())
                        .sessionAttr("userId", user.getId())
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.returnCode").value("0001"))
                .andExpect(jsonPath("$.returnMessage").value("권한이 없습니다."))
                .andDo(print());


    }

    @Test
    void 가게_수정_비로그인() throws Exception {

        User manger = User.builder()
                .name("test")
                .email("test@test.com")
                .password("test")
                .phone("010-1234-5678")
                .role("MANAGER")
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

        String json = mapper.writeValueAsString(param);

        mockMvc.perform(patch("/restaurants/{restaurantId}", restaurant.getId())
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.returnCode").value("0001"))
                .andExpect(jsonPath("$.returnMessage").value("권한이 없습니다."))
                .andDo(print());


    }

    @Test
    void 가게_리스트() throws Exception {

        List<Restaurant> restaurants = new ArrayList<>();

        User manger = User.builder()
                .name("test")
                .email("test@test.com")
                .password("test")
                .phone("010-1234-5678")
                .role("MANAGER")
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

        RestaurantListParam param = new RestaurantListParam("restaurant", 1);

        String json = mapper.writeValueAsString(param);

        mockMvc.perform(get("/restaurants/")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.returnCode").value("0000"))
                .andExpect(jsonPath("$.returnMessage").value("Success"))
                .andExpect(jsonPath("$.data.list.size()").value(5))
                .andExpect(jsonPath("$.data.list[0].name").value("restaurant1"))
                .andExpect(jsonPath("$.data.list[0].restaurantId").value(1))
                .andExpect(jsonPath("$.data.hasNext").value(true))

                .andDo(print());



    }

}