package com.example.easytable.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RestaurantImage {
    private int restaurantImageId;

    private Restaurant restaurant;

    private String imageUrl;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}

