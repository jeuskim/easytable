package com.example.easytable.entity;


import com.example.easytable.dto.front.request.RestaurantModifyRequest;
import com.example.easytable.dto.service.request.RestaurantModifyParam;
import com.example.easytable.entity.base.BaseEntity;
import lombok.*;

import jakarta.persistence.*;

import static lombok.AccessLevel.*;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Restaurant extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String location;
    private String cuisineType;
    private String description;


    @ManyToOne
    @JoinColumn
    private User manager;

    private String openingHours;
    private String closingHours;


    @Builder
    public Restaurant(String name, String location, String cuisineType,
                      User manager, String openingHours, String closingHours, String description) {
        this.name = name;
        this.location = location;
        this.cuisineType = cuisineType;
        this.manager = manager;
        this.openingHours = openingHours;
        this.closingHours = closingHours;
        this.description = description;
    }

    public void modify(RestaurantModifyParam request) {
        this.name = request.getName();
        this.location = request.getLocation();
        this.cuisineType = request.getCuisineType();
        this.openingHours = request.getOpeningHours();
        this.closingHours = request.getClosingHours();
        this.description = request.getDescription();
    }
}
