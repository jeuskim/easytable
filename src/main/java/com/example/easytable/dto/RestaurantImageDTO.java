package com.example.easytable.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RestaurantImageDTO {
    private int restaurantImageId;
    private int restaurantId;
    private String imageUrl;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
