package com.student.student_management.dto;

import java.time.LocalDate;
import java.util.List;

public record CreateAndUpdateCourse (String courseName, Float maxCredit, LocalDate effectiveFrom, LocalDate effectiveUntil, List<Integer> majorIds){
}
