package com.example.easytable.dto.service.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EditReviewParam {

    private String editComment;
    private Integer rating;


}