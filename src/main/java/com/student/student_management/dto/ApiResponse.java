package com.student.student_management.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.http.HttpStatus;

public record ApiResponse<T>(String message, T data, @JsonIgnore HttpStatus httpStatus, Status status) {
}
