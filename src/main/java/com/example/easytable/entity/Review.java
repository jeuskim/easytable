package com.example.easytable.entity;

import com.example.easytable.dto.service.request.EditReviewParam;
import com.example.easytable.entity.base.BaseEntity;
import lombok.*;

import jakarta.persistence.*;

import static lombok.AccessLevel.*;

@Entity
@NoArgsConstructor(access = PROTECTED)
@ToString
@Getter
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    private int rating;
    private String comment;

    @Builder
    public Review(User user, Restaurant restaurant, int rating, String comment) {
        this.user = user;
        this.restaurant = restaurant;
        this.rating = rating;
        this.comment = comment;
    }

    public void edit(EditReviewParam param) {
        this.comment = param.getEditComment();
        this.rating = param.getRating();
    }
}
