package com.example.easytable.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservationDTO {
    private int reservationId;
    private int userId;
    private int restaurantId;
    private LocalDateTime reservationDatetime;
    private int peopleNumber;
    private String status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
