package com.example.easytable.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Reservation {
    private int reservationId;

    private User user;

    private Restaurant restaurant;

    private LocalDateTime reservationDatetime;

    private int peopleNumber;
    private String status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}

