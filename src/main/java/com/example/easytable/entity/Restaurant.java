package com.example.easytable.entity;


import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "Restaurant")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int restaurantId;

    private String name;
    private String location;
    private String cuisineType;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private User manager;

    private String openingHours;
    private String closingHours;

    @Column(name = "create_time", nullable = false)
    private LocalDateTime createTime;

    @Column(name = "update_time", nullable = false)
    private LocalDateTime updateTime;
}
