package com.servosys.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private LocalDateTime timestamp;
    private int status;
    
    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(true, message, data, LocalDateTime.now(), 200);
    }
    
    public static <T> ApiResponse<T> created(T data, String message) {
        return new ApiResponse<>(true, message, data, LocalDateTime.now(), 201);
    }
    
    public static ApiResponse<Void> success(String message) {
        return new ApiResponse<>(true, message, null, LocalDateTime.now(), 200);
    }
    
    public static ApiResponse<Void> error(String message, int status) {
        return new ApiResponse<>(false, message, null, LocalDateTime.now(), status);
    }
}