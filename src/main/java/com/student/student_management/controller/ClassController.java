package com.student.student_management.controller;

import com.student.student_management.dto.ApiResponse;
import com.student.student_management.model.ClassModel;
import com.student.student_management.service.ClassService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/class")
@AllArgsConstructor
public class ClassController {
    private final ClassService classService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ClassModel>>> getAllClasses(){
        ApiResponse<List<ClassModel>> response = classService.getAllClasses();
        return new ResponseEntity<>(response, response.status());
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<ClassModel>> getOneClassById(@PathVariable Long id){
        ApiResponse<ClassModel> response = classService.getOneClassById(id);
        return new ResponseEntity<>(response, response.status());
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ClassModel>> createClass(@RequestBody ClassModel classBody){
        ApiResponse<ClassModel> response = classService.createClass(classBody);
        return new ResponseEntity<>(response, response.status());
    }
    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse<ClassModel>> deleteClass(@PathVariable Long id){
        ApiResponse<ClassModel> response = classService.deleteClassById(id);
        return new ResponseEntity<>(response, response.status());
    }
    @PutMapping("{id}")
    public ResponseEntity<ApiResponse<ClassModel>> updateClassById(@PathVariable Long id, ClassModel classBody){
        ApiResponse<ClassModel> response = classService.updateClassById(id, classBody);
        return new ResponseEntity<>(response, response.status());
    }
}
