package com.example.easytable.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Review {
    private int reviewId;

    private User user;

    private Restaurant restaurant;

    private int rating;
    private String comment;

    private LocalDateTime reviewDatetime;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
