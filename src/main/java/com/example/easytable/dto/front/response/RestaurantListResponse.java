package com.example.easytable.dto.front.response;

import com.example.easytable.entity.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
public class RestaurantListResponse {

    private Long restaurantId;
    private String name;
    private String openingHours;
    private String closingHours;
    private String description;
    private Double rating;


    public RestaurantListResponse(Restaurant restaurant) {
        this.restaurantId = restaurant.getId();
        this.name = restaurant.getName();
        this.openingHours = restaurant.getOpeningHours();
        this.closingHours = restaurant.getClosingHours();
        this.description = restaurant.getDescription();
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}
