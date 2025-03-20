package com.example.easytable.service;

import com.example.easytable.dto.api.request.review.WriteReviewRequest;
import com.example.easytable.dto.api.request.review.EditReviewRequest;
import com.example.easytable.dto.api.response.ReviewListResponse;
import com.example.easytable.dto.service.request.EditReviewParam;
import com.example.easytable.dto.service.request.WriteReviewParam;
import com.example.easytable.entity.Restaurant;
import com.example.easytable.entity.Review;
import com.example.easytable.entity.User;
import com.example.easytable.exception.UnauthorizedException;
import com.example.easytable.repository.RestaurantRepository;
import com.example.easytable.repository.review.ReviewRepository;
import com.example.easytable.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ReviewService {

    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final ReviewRepository reviewRepository;

    public void writeReview(User user, WriteReviewParam param) {
        

        Restaurant restaurant = restaurantRepository.findById(param.getRestaurantId())
                .orElseThrow(() -> new RuntimeException("해당 가게가 없음."));

        Review save = reviewRepository.save(
                Review.builder()
                        .user(user)
                        .restaurant(restaurant)
                        .rating(param.getRating())
                        .comment(param.getComment())
                        .build()

        );

        log.info("키키={}", save);


    }


    public void deleteReview(User user, Long reviewId) {


        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("해당 리뷰가 없음."));

        authCheck(user, review);

        reviewRepository.delete(review);

    }

    public void editReview(User user, Long reviewId, EditReviewParam param) {

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("해당 리뷰 없음."));

        authCheck(user, review);

        review.edit(param);


    }



    public List<ReviewListResponse> getReviews(Long restaurantId) {

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("해당 가게 없음."));

       return reviewRepository.getReviews(restaurant).stream().map(ReviewListResponse::new).toList();

    }

    public Double getAverageRating(Long restaurantId) {

        return reviewRepository.getAverageRating(restaurantId);


    }


    private void authCheck(User user, Review review) {
        if (!review.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedException();
        }
    }




}
