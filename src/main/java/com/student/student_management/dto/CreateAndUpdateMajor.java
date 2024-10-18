package com.student.student_management.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateAndUpdateMajor(
        @NotBlank(message = "Major name is required")
        String majorName,
        @NotNull(message = "department id is required")
        Long departmentId) {
}
