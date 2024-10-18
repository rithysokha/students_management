package com.student.student_management.dto;

import java.util.List;

public record CreateMultipleFaculty(List<CreateAndUpdateFaculty> faculties) {
}
