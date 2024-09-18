package com.student.student_management.controller;

import com.student.student_management.dto.ApiResponse;
import com.student.student_management.dto.CreateAndUpdateStudent;
import com.student.student_management.model.StudentModel;
import com.student.student_management.service.StudentService;
import jakarta.validation.Valid;
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
      return new ResponseEntity<>(response, response.httpStatus());
   }
   @GetMapping("/by-class")
   public ResponseEntity<ApiResponse<List<StudentModel>>> getStudentsByClassId(@RequestParam Long classId){
      ApiResponse<List<StudentModel>> response = studentService.getStudentsByClassId(classId);
      return new ResponseEntity<>(response, response.httpStatus());
   }
   @GetMapping("/by-department")
   public ResponseEntity<ApiResponse<List<StudentModel>>> getStudentsByDepartment(@RequestParam Long departmentId){
      ApiResponse<List<StudentModel>> response = studentService.getStudentsByDepartment(departmentId);
      return new ResponseEntity<>(response, response.httpStatus());
   }

   @GetMapping("{id}")
   public ResponseEntity<ApiResponse<StudentModel>> getOneStudentById(@PathVariable Long id){
      ApiResponse<StudentModel> response = studentService.getOneStudentById(id);
      return new ResponseEntity<>(response, response.httpStatus());
   }

   @PostMapping
   public ResponseEntity<ApiResponse<StudentModel>> createStudent(@Valid @RequestBody CreateAndUpdateStudent studentBody){
      ApiResponse<StudentModel> response = studentService.createStudent(studentBody);
      return new ResponseEntity<>(response, response.httpStatus());
   }
   @DeleteMapping("{id}")
   public ResponseEntity<ApiResponse<StudentModel>> deleteStudentById(@PathVariable Long id){
      ApiResponse<StudentModel> response = studentService.deleteStudentById(id);
      return new ResponseEntity<>(response, response.httpStatus());
   }

   @PutMapping("{id}")
   public ResponseEntity<ApiResponse<StudentModel>> updateStudentById(@PathVariable Long id, @Valid @RequestBody CreateAndUpdateStudent studentBody){
      ApiResponse<StudentModel> response = studentService.updateStudentById(id, studentBody);
      return new ResponseEntity<>(response, response.httpStatus());
   }
}
