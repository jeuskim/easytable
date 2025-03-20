package com.example.easytable.dto.api.request.review;

import com.example.easytable.dto.service.request.WriteReviewParam;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class WriteReviewRequest {

    private Long restaurantId;
    private Integer rating;
    private String comment;
    private List<MultipartFile> images;

    @Builder
    public WriteReviewRequest(Long restaurantId, Integer rating, String comment, List<MultipartFile> images) {
        this.restaurantId = restaurantId;
        this.rating = rating;
        this.comment = comment;
        this.images = images;
    }

    public WriteReviewParam convert() {
        return new WriteReviewParam(restaurantId, rating, comment, images);
    }
}
