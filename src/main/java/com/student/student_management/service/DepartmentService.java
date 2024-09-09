package com.student.student_management.service;

import com.student.student_management.dto.ApiResponse;
import com.student.student_management.model.DepartmentModel;
import com.student.student_management.repository.DepartmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public ApiResponse<List<DepartmentModel>> getAllDeprtments() {
        return new ApiResponse<>("All department", departmentRepository.findAll(), HttpStatus.OK);
    }
}
