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
    private static final Integer OFFSET = 5;


    @Override
    public ListResponse<RestaurantListResponse> findRestaurants(RestaurantListParam request) {


        ListResponse<RestaurantListResponse> response = new ListResponse<>();


        BooleanBuilder builder = new BooleanBuilder();

        if (StringUtils.hasText(request.getName())) {
            builder.and(restaurant.name.contains(request.getName()));
        }


        List<RestaurantListResponse> results = queryFactory
                .select(Projections.constructor(RestaurantListResponse.class,
                        restaurant.id.as("id"),
                        restaurant.name.as("name"),
                        restaurant.openingHours.as("openingHours"),
                        restaurant.closingHours.as("closingHours"),
                        restaurant.description.as("description"),
                        review.rating.avg().coalesce(0.0).as("rating")
                ))
                .from(restaurant)
                .leftJoin(review).on(restaurant.id.eq(review.restaurant.id))
                .groupBy(restaurant.id)
                .where(builder)
                .offset((request.getPage() - 1) * 5L)
                .limit(OFFSET + 1)
                .fetch();

        boolean hasNext = results.size() > OFFSET;

        if (hasNext) {
            results.remove(results.size() - 1);
        }

        response.setList(results);
        response.setHasNext(hasNext);


        return response;
    }
}
