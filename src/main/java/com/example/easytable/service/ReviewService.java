package com.example.easytable.service;

import com.example.easytable.dto.request.review.WriteReviewRequest;
import com.example.easytable.dto.request.review.editReviewRequest;
import com.example.easytable.dto.response.ReviewListResponse;
import com.example.easytable.entity.Restaurant;
import com.example.easytable.entity.Review;
import com.example.easytable.entity.User;
import com.example.easytable.repository.RestaurantRepository;
import com.example.easytable.repository.review.ReviewRepository;
import com.example.easytable.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {

    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final ReviewRepository reviewRepository;

    public void writeReview(WriteReviewRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("해당 유저 없음."));
        Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId())
                .orElseThrow(() -> new RuntimeException("해당 가게가 없음."));

        reviewRepository.save(
                Review.builder()
                        .user(user)
                        .restaurant(restaurant)
                        .rating(request.getRating())
                        .comment(request.getComment())
                        .reviewDatetime(request.getReviewDatetime())
                        .createTime(LocalDateTime.now())
                        .updateTime(LocalDateTime.now())
                        .build()

        );



    }

    public void deleteReview(Integer reviewId){

        reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("해당 리뷰가 없음."));


    }

    public void editReview(Integer userId,editReviewRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("해당 유저 없음."));
        Review review = reviewRepository.findById(request.getReviewId())
                .orElseThrow(() -> new RuntimeException("해당 리뷰 없음."));

        review.edit(request.getEditComment());




    }


    public List<ReviewListResponse> getReviews(Integer restaurantId) {

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("해당 가게 없음."));

       return reviewRepository.getReviews(restaurant).stream().map(ReviewListResponse::new).toList();

    }

    public Double getAverageRating(Integer restaurantId) {

        return reviewRepository.getAverageRating(restaurantId);


    }






}
