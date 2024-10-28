package com.example.Employee_projects.ApiResponse;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
@Getter
@Setter
public class ApiResponse {
    private HttpStatus status;
    private String message;
    private Object data;
    private int statusCode;
    public ApiResponse(HttpStatus status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.statusCode = status.value();
    }
    public ApiResponse(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
        this.statusCode = status.value();
    }
}
