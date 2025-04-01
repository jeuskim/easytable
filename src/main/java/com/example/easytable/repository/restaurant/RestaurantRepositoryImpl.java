package com.example.easytable.repository.restaurant;

import com.example.easytable.dto.front.response.ListResponse;
import com.example.easytable.dto.front.request.RestaurantListRequest;
import com.example.easytable.dto.front.response.RestaurantListResponse;
import com.example.easytable.dto.service.request.RestaurantListParam;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.example.easytable.entity.QRestaurant.restaurant;
import static com.example.easytable.entity.QReview.review;

@Repository
@RequiredArgsConstructor
public class RestaurantRepositoryImpl implements RestaurantRepositoryCustom {

    private final JPAQueryFactory queryFactory;


    @Override
    public Double getRating(Long restaurantId) {


        return queryFactory
                .select(review.rating.avg().coalesce(0.0))
                .from(restaurant)
                .leftJoin(review).on(restaurant.id.eq(review.restaurant.id))
                .where(restaurant.id.eq(restaurantId))
                .groupBy(restaurant.id)
                .fetchOne();


    }
}
