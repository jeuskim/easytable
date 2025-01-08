package com.example.easytable.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewDTO {
    private int reviewId;
    private int userId;
    private int restaurantId;
    private int rating;
    private String comment;
    private LocalDateTime reviewDatetime;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}