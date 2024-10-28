package com.student.student_management.dto;

import jakarta.validation.constraints.NotNull;

public record CreateAndUpdateScore (@NotNull Long studentId, @NotNull Long courseId, @NotNull Float score){
}
