package com.example.easytable.dto.response.restaurant;

import com.example.easytable.entity.Restaurant;
import lombok.Getter;

@Getter
public class RestaurantListResponse {

    private Long restaurantId;
    private String name;

    public RestaurantListResponse(Restaurant restaurant) {
        this.restaurantId = restaurant.getId();
        this.name = restaurant.getName();
    }
}
