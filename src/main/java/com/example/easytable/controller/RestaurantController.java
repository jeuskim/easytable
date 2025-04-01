package com.example.easytable.controller;

import com.example.easytable.dto.front.request.RestaurantListRequest;
import com.example.easytable.dto.front.request.RestaurantModifyRequest;
import com.example.easytable.dto.front.request.RestaurantRegisterRequest;
import com.example.easytable.dto.front.response.ApiResponse;
import com.example.easytable.dto.front.response.ListResponse;
import com.example.easytable.dto.front.response.RestaurantListResponse;
import com.example.easytable.dto.service.request.RestaurantListParam;
import com.example.easytable.entity.Restaurant;
import com.example.easytable.entity.User;
import com.example.easytable.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;


    @PostMapping
    public ApiResponse registerRestaurant(User user, @RequestBody RestaurantRegisterRequest request) {

        restaurantService.registerRestaurant(user, request.convert());

        return ApiResponse.success();

    }

    @PatchMapping("/{restaurantId}")
    public ApiResponse modifyRestaurant(User user, @PathVariable Long restaurantId,
                                        @RequestBody RestaurantModifyRequest request) {

        restaurantService.modifyRestaurant(user, restaurantId, request.convert());

        return ApiResponse.success();
    }

    @GetMapping
    public ApiResponse<ListResponse<RestaurantListResponse>> getRestaurants(@RequestParam String name, @RequestParam(defaultValue = "1") Integer page) {

        Slice<Restaurant> slice = restaurantService.getRestaurants(name, page);
        ListResponse<RestaurantListResponse> response = new ListResponse<>();


        response.setList(slice.map(restaurant -> {
            RestaurantListResponse listResponse = new RestaurantListResponse(restaurant);
            listResponse.setRating(restaurantService.getRating(restaurant.getId()));
            return listResponse;
        }).getContent());

        response.setHasNext(slice.hasNext());

        return ApiResponse.success(response);

    }

}
