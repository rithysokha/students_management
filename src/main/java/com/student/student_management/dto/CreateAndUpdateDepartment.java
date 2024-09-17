package com.student.student_management.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateAndUpdateDepartment(
                @NotBlank(message = "Department name is required")
                @Size(max = 20, message = "Department name must be lest than 21 character")
                String departmentName
) {
}
