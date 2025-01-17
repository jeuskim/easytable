package com.example.easytable.repository.review;

import com.example.easytable.entity.QReview;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.example.easytable.entity.QReview.*;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl  implements ReviewRepositoryCustom{

    private final JPAQueryFactory queryFactory;


    public Double getAverageRating(Integer restaurantId) {

        return queryFactory
                .select(review.rating.avg().coalesce(0.0))
                .from(review)
                .where(review.restaurant.restaurantId.eq(restaurantId))
                .fetchOne();


    }


}
