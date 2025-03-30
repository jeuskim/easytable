package com.example.easytable.entity;


import com.example.easytable.entity.base.BaseEntity;
import lombok.*;

import jakarta.persistence.*;
import java.time.LocalDateTime;

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

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private User manager;

    private String openingHours;
    private String closingHours;
    @Builder
    public Restaurant(String name, String location, String cuisineType, User manager, String openingHours, String closingHours) {
        this.name = name;
        this.location = location;
        this.cuisineType = cuisineType;
        this.manager = manager;
        this.openingHours = openingHours;
        this.closingHours = closingHours;
    }
}
