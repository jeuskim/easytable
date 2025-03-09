package com.example.easytable.dto.front.request;

import com.example.easytable.dto.service.request.RestaurantRegisterParam;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;


@Getter
public class RestaurantRegisterRequest {

    @NotBlank(message = "가게 이름은 필수 항목입니다.")
    private String name;

    @NotBlank(message = "주소는 필수 항목입니다.")
    private String location;

    @NotBlank(message = "운영 시간은 필수 항목입니다.")
    private String openingHours;

    @NotBlank(message = "마감 시간은 필수 항목입니다.")
    private String closingHours;

    @NotBlank(message = "카테고리는 필수 항목입니다.")
    private String cuisineType;

    @NotBlank(message = "가게 설명은 필수 항목입니다.")
    private String description;


    public RestaurantRegisterParam convert() {

        return new RestaurantRegisterParam(
                this.name,
                this.location,
                this.openingHours,
                this.closingHours,
                this.cuisineType,
                this.description
        );
    }


}
