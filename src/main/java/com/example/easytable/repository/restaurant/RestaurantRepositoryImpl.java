package com.example.easytable.repository.restaurant;

import com.example.easytable.dto.response.ListResponse;
import com.example.easytable.dto.request.RestaurantListRequest;
import com.example.easytable.dto.response.RestaurantListResponse;
import com.example.easytable.repository.restaurant.RestaurantRepositoryCustom;
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
public class RestaurantRepositoryImpl  implements RestaurantRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private static final Integer OFFSET = 5;


    @Override
    public ListResponse<RestaurantListResponse> findRestaurants(RestaurantListRequest request) {


        ListResponse<RestaurantListResponse> response = new ListResponse<>();


        BooleanBuilder builder = new BooleanBuilder();

        if (StringUtils.hasText(request.getName())) {
            builder.and(restaurant.name.contains(request.getName()));
        }



        List<RestaurantListResponse> results = queryFactory
                .select(Projections.constructor(RestaurantListResponse.class,
                        restaurant.restaurantId.as("restaurantId"),
                        restaurant.name.as("name"),
                        restaurant.openingHours.as("openingHours"),
                        restaurant.closingHours.as("closingHours"),
                        restaurant.description.as("description"),
                        review.rating.avg().coalesce(0.0).as("rating")
                ))
                .from(restaurant)
                .leftJoin(review).on(restaurant.restaurantId.eq(review.restaurant.restaurantId))
                .groupBy(restaurant.restaurantId)
                .where(builder)
                .offset(OFFSET)
                .limit(request.getPage() + 1)
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
