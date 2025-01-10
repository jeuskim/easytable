package com.example.easytable.entity;

import lombok.*;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import static lombok.AccessLevel.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "Reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reservationId;

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

    @Column(name = "create_time", nullable = false)
    private LocalDateTime createTime;

    @Column(name = "update_time", nullable = false)
    private LocalDateTime updateTime;


    public void cancel() {
        this.status = "CANCEL";
    }

    @Builder
    public Reservation(User user, Restaurant restaurant, LocalDateTime reservationDatetime, int peopleNumber, String status, LocalDateTime createTime, LocalDateTime updateTime) {
        this.user = user;
        this.restaurant = restaurant;
        this.reservationDatetime = reservationDatetime;
        this.peopleNumber = peopleNumber;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }
}
