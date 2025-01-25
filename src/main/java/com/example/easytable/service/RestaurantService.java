package com.example.easytable.service;

import com.example.easytable.dto.request.RestaurantListRequest;
import com.example.easytable.dto.request.RestaurantModifyRequest;
import com.example.easytable.dto.request.RestaurantRegisterRequest;
import com.example.easytable.dto.response.ListResponse;
import com.example.easytable.dto.response.RestaurantListResponse;
import com.example.easytable.entity.Restaurant;
import com.example.easytable.entity.User;
import com.example.easytable.repository.restaurant.RestaurantRepository;
import com.example.easytable.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    public void registerRestaurant(Integer userId, RestaurantRegisterRequest request) {

        User manager = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("해당 유저가 없습니다."));

        restaurantRepository.save(
                Restaurant.builder()
                        .name(request.getName())
                        .location(request.getLocation())
                        .cuisineType(request.getCuisineType())
                        .manager(manager)
                        .openingHours(request.getOpeningHours())
                        .closingHours(request.getClosingHours())
                        .createTime(LocalDateTime.now())
                        .updateTime(LocalDateTime.now())
                        .build()

        );



    }

    public void modifyRestaurant(Integer userId, RestaurantModifyRequest request) {

        User manager = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("해당 유저가 없습니다."));

        Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId())
                .orElseThrow(() -> new RuntimeException("해당 가게가 없음."));


        if (!restaurant.getManager().equals(manager)) {
            throw new RuntimeException("가게를 수정할 권한이 없습니다.");
        }

        restaurant.modify(request);


    }

    public ListResponse<RestaurantListResponse> getRestaurants(RestaurantListRequest request) {
       return restaurantRepository.findRestaurants(request);
    }





}
