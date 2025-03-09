package com.example.easytable.dto.front.request;

import com.example.easytable.dto.service.request.RestaurantListParam;
import lombok.Getter;

@Getter
public class RestaurantListRequest {

    private String name;
    private Integer page;


    public RestaurantListParam convert() {
        return new RestaurantListParam(this.name, this.page);
    }


}
