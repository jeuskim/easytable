package com.example.easytable.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponse<T> {

    private String returnCode;
    private String returnMessage;
    private T data;

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>("0000", "Success", data);
    }

    public static <T> ApiResponse<T> fail(String returnMessage) {
        return new ApiResponse<>("0001", returnMessage, null);
    }
}
