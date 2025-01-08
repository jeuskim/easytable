package com.example.easytable.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Restaurant {
    private int restaurantId;

    private String name;
    private String location;
    private String cuisineType;

    private User manager;

    private String openingHours;
    private String closingHours;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}

