package com.student.student_management.controller;

import com.student.student_management.dto.ApiResponse;
import com.student.student_management.dto.CreateAndUpdateStudent;
import com.student.student_management.model.StudentModel;
import com.student.student_management.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/student")
@AllArgsConstructor
public class StudentController {
   private final StudentService studentService;

   @GetMapping
   public ResponseEntity<ApiResponse<List<StudentModel>>> getAllStudents(){
      ApiResponse<List<StudentModel>> response = studentService.getAllStudents();
      return new ResponseEntity<>(response, response.status());
   }

   @GetMapping("{id}")
   public ResponseEntity<ApiResponse<StudentModel>> getOneStudentById(@PathVariable Long id){
      ApiResponse<StudentModel> response = studentService.getOneStudentById(id);
      return new ResponseEntity<>(response, response.status());
   }

   @PostMapping
   public ResponseEntity<ApiResponse<StudentModel>> createStudent(@RequestBody CreateAndUpdateStudent studentBody){
      ApiResponse<StudentModel> response = studentService.createStudent(studentBody);
      return new ResponseEntity<>(response, response.status());
   }
   @DeleteMapping("{id}")
   public ResponseEntity<ApiResponse<String>> deleteStudentById(@PathVariable Long id){
      ApiResponse<String> response = studentService.deleteStudentById(id);
      return new ResponseEntity<>(response, response.status());
   }

   @PutMapping("{id}")
   public ResponseEntity<ApiResponse<StudentModel>> updateStudentById(@PathVariable Long id, @RequestBody CreateAndUpdateStudent studentBody){
      ApiResponse<StudentModel> response = studentService.updateStudentById(id, studentBody);
      return new ResponseEntity<>(response, response.status());
   }
}
