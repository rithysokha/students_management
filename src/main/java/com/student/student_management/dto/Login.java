package com.student.student_management.dto;

import jakarta.validation.constraints.NotBlank;

public record Login(
        @NotBlank
        String username,
        @NotBlank
        String password) {
}
