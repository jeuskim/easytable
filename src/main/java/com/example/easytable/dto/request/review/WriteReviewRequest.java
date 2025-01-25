package com.example.easytable.dto.request.review;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class WriteReviewRequest {

    private Integer userId;
    private Integer restaurantId;
    private Integer rating;
    private String comment;
    private LocalDateTime reviewDatetime;
    private List<MultipartFile> images;

}
