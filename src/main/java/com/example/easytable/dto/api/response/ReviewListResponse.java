package com.example.easytable.dto.api.response;

import com.example.easytable.entity.Review;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ReviewListResponse {

    private Long reviewId;
    private String userName;
    private String comment;
    private Integer rating;


    public ReviewListResponse(Review review) {
        this.reviewId = review.getId();
        this.userName = review.getUser().getName();
        this.comment = review.getComment();
        this.rating = review.getRating();
    }
}
