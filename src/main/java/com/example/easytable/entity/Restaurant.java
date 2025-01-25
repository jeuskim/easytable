package com.example.easytable.entity;


import com.example.easytable.dto.request.RestaurantModifyRequest;
import lombok.*;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import static lombok.AccessLevel.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "Restaurant")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int restaurantId;

    private String name;
    private String location;
    private String cuisineType;
    private String description;


    @ManyToOne
    @JoinColumn(name = "manager_id")
    private User manager;

    private String openingHours;
    private String closingHours;

    @Column(name = "create_time", nullable = false)
    private LocalDateTime createTime;

    @Column(name = "update_time", nullable = false)
    private LocalDateTime updateTime;
    @Builder
    public Restaurant(String name, String location, String cuisineType,
                      User manager, String openingHours, String closingHours,
                      LocalDateTime createTime, LocalDateTime updateTime) {
        this.name = name;
        this.location = location;
        this.cuisineType = cuisineType;
        this.manager = manager;
        this.openingHours = openingHours;
        this.closingHours = closingHours;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public void modify(RestaurantModifyRequest request) {
        this.name = request.getName();
        this.location = request.getLocation();
        this.cuisineType = request.getCuisineType();
        this.openingHours = request.getOpeningHours();
        this.closingHours = request.getClosingHours();

    }
}
