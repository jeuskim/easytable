package com.example.easytable.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReviewImage {
    private int reviewImageId;

    private Review review;

    private String imageUrl;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}

