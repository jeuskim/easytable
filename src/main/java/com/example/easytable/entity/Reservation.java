package com.example.easytable.entity;

import com.example.easytable.entity.base.BaseEntity;
import lombok.*;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import static lombok.AccessLevel.*;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Reservation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @Column(name = "reservation_datetime", nullable = false)
    private LocalDateTime reservationDatetime;

    private int peopleNumber;
    private String status;

    @Builder
    public Reservation(User user, Restaurant restaurant, LocalDateTime reservationDatetime, int peopleNumber, String status) {
        this.user = user;
        this.restaurant = restaurant;
        this.reservationDatetime = reservationDatetime;
        this.peopleNumber = peopleNumber;
        this.status = status;
    }

    public void cancel() {
        this.status = "CANCEL";
    }
}
