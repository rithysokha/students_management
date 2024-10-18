package com.student.student_management.controller;

import com.student.student_management.dto.ApiResponse;
import com.student.student_management.dto.CreateAndUpdateMajor;
import com.student.student_management.model.MajorModel;
import com.student.student_management.service.MajorService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/major")
@AllArgsConstructor
public class MajorController {
    private final MajorService majorService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<MajorModel>>> getAllMajors() {
        ApiResponse<List<MajorModel>> response = majorService.getAllMajors();
        return new ResponseEntity<>(response, response.httpStatus());
    }

    @GetMapping("/by-department")
    public ResponseEntity<ApiResponse<List<MajorModel>>> getMajorsByDepartmentId(@RequestParam Long departmentId) {
        ApiResponse<List<MajorModel>> response = majorService.getClassesByDepartmentId(departmentId);
        return new ResponseEntity<>(response, response.httpStatus());
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<MajorModel>> getOneMajorById(@PathVariable Long id) {
        ApiResponse<MajorModel> response = majorService.getOneMajorById(id);
        return new ResponseEntity<>(response, response.httpStatus());
    }

    @PostMapping
    public ResponseEntity<ApiResponse<MajorModel>> createMajor(@Valid @RequestBody CreateAndUpdateMajor classBody) {
        ApiResponse<MajorModel> response = majorService.createMajor(classBody);
        return new ResponseEntity<>(response, response.httpStatus());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse<MajorModel>> deleteMajor(@PathVariable Long id) {
        ApiResponse<MajorModel> response = majorService.deleteMajorById(id);
        return new ResponseEntity<>(response, response.httpStatus());
    }

    @PutMapping("{id}")
    public ResponseEntity<ApiResponse<MajorModel>> updateMajorById(@PathVariable Long id, @Valid @RequestBody CreateAndUpdateMajor classBody) {
        ApiResponse<MajorModel> response = majorService.updateClassById(id, classBody);
        return new ResponseEntity<>(response, response.httpStatus());
    }
}
