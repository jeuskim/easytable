package com.example.easytable.dto.api.request.review;

import com.example.easytable.dto.service.request.EditReviewParam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EditReviewRequest {

    private String editComment;
    private Integer rating;

    public EditReviewParam convert() {
        return new EditReviewParam(editComment, rating);
    }


}