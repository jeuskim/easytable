package com.example.easytable.repository.restaurant;

import com.example.easytable.dto.front.response.ListResponse;
import com.example.easytable.dto.front.request.RestaurantListRequest;
import com.example.easytable.dto.front.response.RestaurantListResponse;
import com.example.easytable.dto.service.request.RestaurantListParam;

public interface RestaurantRepositoryCustom {
    ListResponse<RestaurantListResponse> findRestaurants(RestaurantListParam request);


}
