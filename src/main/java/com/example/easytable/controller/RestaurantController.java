package com.example.easytable.controller;

import com.example.easytable.dto.request.RestaurantListRequest;
import com.example.easytable.dto.request.RestaurantModifyRequest;
import com.example.easytable.dto.request.RestaurantRegisterRequest;
import com.example.easytable.dto.response.ListResponse;
import com.example.easytable.dto.response.RestaurantListResponse;
import com.example.easytable.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@RestController
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;


    public void registerRestaurant(@SessionAttribute("userId") Integer userId,
                                   @RequestBody RestaurantRegisterRequest request) {

        restaurantService.registerRestaurant(userId, request);
    }

    public void modifyRestaurant(@SessionAttribute("userId") Integer userId,
                                 @RequestBody RestaurantModifyRequest request) {

        restaurantService.modifyRestaurant(userId, request);

    }

    public ListResponse<RestaurantListResponse> getRestaurants(@RequestBody RestaurantListRequest request) {

        return restaurantService.getRestaurants(request);

    }

}
