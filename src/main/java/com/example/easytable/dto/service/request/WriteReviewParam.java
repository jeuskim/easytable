package com.example.easytable.dto.service.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@NoArgsConstructor
public class WriteReviewParam {

    private Long restaurantId;
    private Integer rating;
    private String comment;
    private List<MultipartFile> images;

    @Builder
    public WriteReviewParam(Long restaurantId, Integer rating, String comment, List<MultipartFile> images) {
        this.restaurantId = restaurantId;
        this.rating = rating;
        this.comment = comment;
        this.images = images;
    }
}
