package com.student.student_management.dto;

import org.springframework.http.HttpStatus;

public record ApiResponse<T>(String message, T data, HttpStatus status) {
}
