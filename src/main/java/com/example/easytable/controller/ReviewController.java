package com.example.easytable.controller;

import com.example.easytable.dto.request.review.WriteReviewRequest;
import com.example.easytable.dto.request.review.editReviewRequest;
import com.example.easytable.dto.response.ReviewListResponse;
import com.example.easytable.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;


    @PostMapping("/write")
    public void writeReview(@RequestBody WriteReviewRequest request) {

        reviewService.writeReview(request);


    }

    @DeleteMapping("/{reviewId}")
    public void deleteReview(@PathVariable Integer reviewId) {
        reviewService.deleteReview(reviewId);

    }

    @PatchMapping("/edit")
    public void editReview(Integer userId, editReviewRequest request) {

        reviewService.editReview(userId, request);


    }

    @GetMapping("/{restaurantId}")
    public List<ReviewListResponse> getReviews(@PathVariable Integer restaurantId) {

        return reviewService.getReviews(restaurantId);

    }

    @GetMapping("/{restaurantId}/rating")
    public Double getAverageRating(@PathVariable Integer restaurantId) {

        return reviewService.getAverageRating(restaurantId);

    }

}
