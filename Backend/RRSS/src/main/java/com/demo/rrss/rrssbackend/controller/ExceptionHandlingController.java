package com.demo.rrss.rrssbackend.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class ExceptionHandlingController {
    @Getter
    @Setter
    public static class ErrorResponse {
        private String status;
        private String message;
        private String detail;
        private String timestamp;

        public ErrorResponse(String status, String message, String detail, LocalDateTime timestamp) {
            this.status = status;
            this.message = message;
            this.detail = detail;
            this.timestamp = timestamp.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        }
    }
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleException(ResponseStatusException e) {
        HttpStatusCode status = e.getStatusCode();
        String message;
        if (status.equals(UNAUTHORIZED)) {
            message = "Please check your credentials and try again.";
        } else if (status.equals(BAD_REQUEST)) {
            message = "Please check your input and try again.";
        } else if (status.equals(FORBIDDEN)) {
            message = "You do not have permission to access this resource. Please login if you haven't already done so.";
        } else if (status.equals(NOT_FOUND)) {
            message = "The resource you're looking for was not found.";
        } else if (status.equals(INTERNAL_SERVER_ERROR)) {
            message = "An internal server error occurred. Please try again later.";
        } else if (status.equals(CONFLICT)) {
            message = "A conflict occurred. Please check your input and try again.";
        } else {
            message = "An unexpected error occurred. Please contact the administrator";
        }
        ErrorResponse errorResponse = new ErrorResponse(status.toString(), e.getReason(),message, LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, status);
    }
}
