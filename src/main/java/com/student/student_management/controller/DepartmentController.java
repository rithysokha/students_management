package com.student.student_management.controller;

import com.student.student_management.dto.ApiResponse;
import com.student.student_management.dto.CreateAndUpdateDepartment;
import com.student.student_management.model.DepartmentModel;
import com.student.student_management.service.DepartmentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/department")
@AllArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<DepartmentModel>>> getAllDepartments(){
        ApiResponse<List<DepartmentModel>> response = departmentService.getAllDepartments();
        return new ResponseEntity<>(response, response.status());
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<DepartmentModel>> getOneDepartmentById(@PathVariable Long id){
        ApiResponse<DepartmentModel> response = departmentService.getOneDepartmentById(id);
        return new ResponseEntity<>(response, response.status());
    }

    @PostMapping
    public ResponseEntity<ApiResponse<DepartmentModel>> createDepartment(@RequestBody CreateAndUpdateDepartment departmentBody){
        ApiResponse<DepartmentModel> response = departmentService.createDepartment(departmentBody);
        return new ResponseEntity<>(response, response.status());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse<String>> deleteDepartmentById(@PathVariable Long id){
        ApiResponse<String> response = departmentService.deleteDepartmentById(id);
        return new ResponseEntity<>(response, response.status());
    }

    @PutMapping("{id}")
    public ResponseEntity<ApiResponse<DepartmentModel>> updateDepartmentById(@PathVariable Long id, @RequestBody CreateAndUpdateDepartment departmentBody){
        ApiResponse<DepartmentModel> response = departmentService.updateDepartmentById(id, departmentBody);
        return new ResponseEntity<>(response, response.status());
    }
}