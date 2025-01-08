package com.example.easytable.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewImageDTO {
    private int reviewImageId;
    private int reviewId;
    private String imageUrl;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}