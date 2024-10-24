package com.student.student_management.controller;

import com.student.student_management.dto.ApiResponse;
import com.student.student_management.dto.CreateAndUpdateCourse;
import com.student.student_management.model.CourseModel;
import com.student.student_management.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/course")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<CourseModel>>> getAllCourses(){
        ApiResponse<List<CourseModel>> response = courseService.getAllCourses();
        return new ResponseEntity<>(response, response.httpStatus());
    }
    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<CourseModel>> getCourseById(@PathVariable Long id){
        ApiResponse<CourseModel> response = courseService.getCourseById(id);
        return new ResponseEntity<>(response, response.httpStatus());
    }
    @PostMapping
    public ResponseEntity<ApiResponse<CourseModel>> createNewCourse(@Valid @RequestBody CreateAndUpdateCourse body){
        ApiResponse<CourseModel> response = courseService.createNewCourse(body);
        return new ResponseEntity<>(response, response.httpStatus());
    }
    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse<CourseModel>> deleteCourseById(@PathVariable Long id){
        ApiResponse<CourseModel> response = courseService.deleteCourseById(id);
        return new ResponseEntity<>(response, response.httpStatus());
    }
    @PutMapping("{id}")
    public ResponseEntity<ApiResponse<CourseModel>> updateCourseById(@PathVariable Long id, @Valid @RequestBody CreateAndUpdateCourse body){
        ApiResponse<CourseModel> response = courseService.updateCourseById(id, body);
        return new ResponseEntity<>(response, response.httpStatus());
    }
}
