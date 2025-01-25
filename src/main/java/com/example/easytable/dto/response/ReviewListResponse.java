package com.example.easytable.dto.response;

import com.example.easytable.entity.Review;
import lombok.Getter;

@Getter
public class ReviewListResponse {

    private Integer reviewId;
    private String userName;
    private String comment;
    private Integer rating;


    public ReviewListResponse(Review review) {
        this.reviewId = review.getReviewId();
        this.userName = review.getUser().getName();
        this.comment = review.getComment();
        this.rating = review.getRating();
    }
}
