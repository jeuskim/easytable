package com.example.easytable.dto.response.restaurant;

import com.example.easytable.entity.Restaurant;
import lombok.Getter;

@Getter
public class RestaurantListResponse {

    private int restaurantId;
    private String name;

    public RestaurantListResponse(Restaurant restaurant) {
        this.restaurantId = restaurant.getRestaurantId();
        this.name = restaurant.getName();
    }
}
