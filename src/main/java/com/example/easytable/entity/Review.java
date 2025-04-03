package com.example.easytable.entity;

import com.example.easytable.entity.base.BaseEntity;
import com.example.easytable.entity.user.User;
import lombok.*;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import static lombok.AccessLevel.*;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
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

    @Builder
    public Review(User user, Restaurant restaurant, int rating, String comment, LocalDateTime reviewDatetime) {
        this.user = user;
        this.restaurant = restaurant;
        this.rating = rating;
        this.comment = comment;
    }
}
