package com.student.student_management.dto;

import jakarta.validation.constraints.NotNull;

public record CreateAndUpdateFaculty(@NotNull String facultyName) {
}
