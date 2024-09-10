package com.student.student_management.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateAndUpdateClass(
        @NotBlank(message = "Class name is required")
        String className,
        @NotNull(message = "department id is required")
        Long departmentId) {
}
