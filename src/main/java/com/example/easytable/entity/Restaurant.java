package com.example.easytable.entity;


import com.example.easytable.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
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


}
