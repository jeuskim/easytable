package com.example.easytable.dto.service.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class RestaurantRegisterParam {
    
    private String name;


    private String location;


    private String openingHours;


    private String closingHours;


    private String cuisineType;


    private String description;


}

