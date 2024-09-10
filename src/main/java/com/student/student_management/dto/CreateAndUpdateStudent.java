package com.student.student_management.dto;

import java.time.LocalDate;

public record CreateAndUpdateStudent (String firstName, String lastName, LocalDate dateOfBirth, String address){
}
