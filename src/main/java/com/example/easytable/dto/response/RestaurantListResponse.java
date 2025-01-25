package com.example.easytable.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RestaurantListResponse {

    private Integer restaurantId;
    private String name;
    private String openingHours;
    private String closingHours;
    private String description;
    private Double rating;


}
