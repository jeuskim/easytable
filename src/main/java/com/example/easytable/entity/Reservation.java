package com.example.easytable.entity;

import com.example.easytable.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
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

}
