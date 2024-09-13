package com.student.student_management.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

public record CreateAndUpdateStudent (
        @NotBlank(message = "First name is required")
        @Size(max = 50, message = "First name must be less than 51 character")
        String firstName,
        @NotBlank(message = "Last name is required")
        @Size(max = 50, message = "Last name must be less than 51 character")
        String lastName,
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        @NotNull(message = "Date of birth is required")
        LocalDate dateOfBirth,
        @NotBlank(message = "Address is required")
        String address,
        @NotNull
        Long classId,
        @NotBlank(message = "Phone number is required")
        @Size(min = 10, max = 12, message = "Phone number must be in range from 10 to 12 character")
        String phoneNumber,
        MultipartFile picture

){
}
