package com.student.student_management.dto;

import com.student.student_management.model.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record Register(
        @NotBlank
        String username,
        @NotBlank
        String password,
        @NotNull
        Role role) {
}
