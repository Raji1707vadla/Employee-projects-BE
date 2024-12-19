package com.example.Employee_projects.exception;

import com.example.Employee_projects.ApiResponse.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleExceptions(Exception exception, WebRequest request) {

        if (exception instanceof BadRequestException) {
            return composeBadRequestResponse(exception, request);
        } else if (exception instanceof UnauthorizedException) {
            return composeUnAuthorisedExceptionResponse(exception, request);
        } else {
            return composeGenericException(exception, request);
        }
    }

    private ResponseEntity<ApiResponse> composeConstraintViolationExceptionResponse(Exception exception, WebRequest request) {

        return new ResponseEntity<>(composeAppResponseDTO(HttpStatus.NOT_IMPLEMENTED, exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ApiResponse> composeGenericException(Exception exception, WebRequest request) {
        return new ResponseEntity<>(composeAppResponseDTO(HttpStatus.BAD_REQUEST, exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ApiResponse> composeUnAuthorisedExceptionResponse(Exception exception, WebRequest request) {
        return new ResponseEntity<>(composeAppResponseDTO(HttpStatus.UNAUTHORIZED, exception.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    private ResponseEntity<ApiResponse> composeIdNotFoundExceptionResponse(Exception exception, WebRequest request) {
        return new ResponseEntity<>(composeAppResponseDTO(HttpStatus.NOT_FOUND, exception.getMessage()), HttpStatus.ALREADY_REPORTED);
    }

    private ResponseEntity<ApiResponse> composeDuplicateExceptionResponse(Exception exception, WebRequest request) {
        return new ResponseEntity<>(composeAppResponseDTO(HttpStatus.ALREADY_REPORTED, exception.getMessage()), HttpStatus.ALREADY_REPORTED);
    }


    private ResponseEntity<ApiResponse> composeBadRequestResponse(Exception exception, WebRequest request) {
        return new ResponseEntity<>(composeAppResponseDTO(HttpStatus.BAD_REQUEST, exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    private ApiResponse composeAppResponseDTO(HttpStatus status, String message) {
        return new ApiResponse(status, message);
    }

}

