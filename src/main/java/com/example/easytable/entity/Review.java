package com.example.easytable.entity;

import lombok.*;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import static lombok.AccessLevel.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "Review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reviewId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    private int rating;
    private String comment;

    @Column(name = "review_datetime", nullable = false)
    private LocalDateTime reviewDatetime;

    @Column(name = "create_time", nullable = false)
    private LocalDateTime createTime;

    @Column(name = "update_time", nullable = false)
    private LocalDateTime updateTime;

    @Builder
    public Review(User user, Restaurant restaurant, int rating, String comment, LocalDateTime reviewDatetime, LocalDateTime createTime, LocalDateTime updateTime) {
        this.user = user;
        this.restaurant = restaurant;
        this.rating = rating;
        this.comment = comment;
        this.reviewDatetime = reviewDatetime;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public void edit(String comment) {
        this.comment = comment;
    }
}
