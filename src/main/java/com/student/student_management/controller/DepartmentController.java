package com.student.student_management.controller;

import com.student.student_management.dto.ApiResponse;
import com.student.student_management.model.DepartmentModel;
import com.student.student_management.service.DepartmentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/department")
@AllArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<DepartmentModel>>> getAllDepartments(){
        ApiResponse<List<DepartmentModel>> response = departmentService.getAllDeprtments();
        return new ResponseEntity<>(response, response.status());
    }
}
