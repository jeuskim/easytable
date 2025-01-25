package com.example.easytable.repository.restaurant;

import com.example.easytable.dto.response.ListResponse;
import com.example.easytable.dto.request.RestaurantListRequest;
import com.example.easytable.dto.response.RestaurantListResponse;

public interface RestaurantRepositoryCustom {

    ListResponse<RestaurantListResponse> findRestaurants(RestaurantListRequest request);

}
