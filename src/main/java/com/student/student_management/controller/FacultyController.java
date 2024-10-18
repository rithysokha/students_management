package com.student.student_management.controller;

import com.student.student_management.dto.ApiResponse;
import com.student.student_management.dto.CreateAndUpdateFaculty;
import com.student.student_management.dto.CreateMultipleFaculty;
import com.student.student_management.model.FacultyModel;
import com.student.student_management.service.FacultyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/faculty")
public class FacultyController {
    private final FacultyService facultyService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<FacultyModel>>> getAllFaculty(){
        ApiResponse<List<FacultyModel>> response = facultyService.getAllFaculty();
        return new ResponseEntity<>(response, response.httpStatus());
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<FacultyModel>> getOneFacultyByID(@PathVariable Long id){
        ApiResponse<FacultyModel> response = facultyService.getOneFacultyById(id);
        return new ResponseEntity<>(response, response.httpStatus());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse<FacultyModel>> deleteFacultyByID(@PathVariable Long id){
        ApiResponse<FacultyModel> response = facultyService.deleteFacultyById(id);
        return new ResponseEntity<>(response, response.httpStatus());
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<List<Long>>> deleteMultipleFaculty(@RequestParam List<Long> ids){
        ApiResponse<List<Long>> response = facultyService.deleteMultipleFaculty(ids);
        return new ResponseEntity<>(response, response.httpStatus());
    }

    @PostMapping
    public ResponseEntity<ApiResponse<FacultyModel>> createFaculty(@RequestBody @Valid CreateAndUpdateFaculty body){
        ApiResponse<FacultyModel> response = facultyService.createFaculty(body);
        return new ResponseEntity<>(response, response.httpStatus());
    }

    @PostMapping("/multiple")
    public ResponseEntity<ApiResponse<List<FacultyModel>>> createFaculty(@RequestBody @Valid CreateMultipleFaculty body){
        ApiResponse<List<FacultyModel>> response = facultyService.createMultipleFaculty(body);
        return new ResponseEntity<>(response, response.httpStatus());
    }

    @PutMapping("{id}")
    public ResponseEntity<ApiResponse<FacultyModel>> updateFacultyById(@PathVariable Long id, @RequestBody @Valid CreateAndUpdateFaculty body){
        ApiResponse<FacultyModel> response = facultyService.updateFacultyById(id, body);
        return new ResponseEntity<>(response, response.httpStatus());
    }
}
