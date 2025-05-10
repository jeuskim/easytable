package com.example.easytable.controller;

import com.example.easytable.dto.api.request.review.WriteReviewRequest;
import com.example.easytable.dto.api.request.review.EditReviewRequest;
import com.example.easytable.dto.api.response.ApiResponse;
import com.example.easytable.dto.api.response.ReviewListResponse;
import com.example.easytable.entity.User;
import com.example.easytable.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;


    @PostMapping("/write")
    public ApiResponse writeReview(User user, @RequestBody WriteReviewRequest request) {


        log.info("하하");
        reviewService.writeReview(user, request.convert());

        return ApiResponse.success(null);

    }

    @DeleteMapping("/{reviewId}")
    public ApiResponse deleteReview(User user, @PathVariable Long reviewId) {

        reviewService.deleteReview(user, reviewId);

        return ApiResponse.success(null);

    }

    @PatchMapping("/{reviewId}")
    public ApiResponse editReview(User user, @PathVariable Long reviewId, @RequestBody EditReviewRequest request) {

        reviewService.editReview(user, reviewId, request.convert());

        return ApiResponse.success(null);


    }

    @GetMapping("/{restaurantId}")
    public ApiResponse<List<ReviewListResponse>> getReviews(@PathVariable Long restaurantId) {

        return ApiResponse.success(reviewService.getReviews(restaurantId));

    }

    @GetMapping("/{restaurantId}/rating")
    public ApiResponse<Double> getAverageRating(@PathVariable Long restaurantId) {

        return ApiResponse.success(reviewService.getAverageRating(restaurantId));

    }

}
