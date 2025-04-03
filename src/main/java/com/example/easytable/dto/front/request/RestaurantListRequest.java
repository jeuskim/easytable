package com.example.easytable.dto.front.request;

import com.example.easytable.dto.service.request.RestaurantListParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantListRequest {

    private String name;
    private Integer page;


    public RestaurantListParam convert() {
        return new RestaurantListParam(this.name, this.page);
    }


}
