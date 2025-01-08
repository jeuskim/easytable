package com.example.easytable.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RestaurantDTO {
    private int restaurantId;
    private int managerId;
    private String name;
    private String location;
    private String cuisineType;
    private String openingHours;
    private String closingHours;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
