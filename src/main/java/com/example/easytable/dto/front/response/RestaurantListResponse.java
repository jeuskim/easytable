package com.example.easytable.dto.front.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class RestaurantListResponse {

    private Long restaurantId;
    private String name;
    private String openingHours;
    private String closingHours;
    private String description;
    private Double rating;


}
