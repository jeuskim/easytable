package com.example.easytable.entity;

import com.example.easytable.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Restaurant restaurant;

    private int rating;
    private String comment;

    @Column(name = "review_datetime", nullable = false)
    private LocalDateTime reviewDatetime;

}
