package com.student.student_management.controller;

import com.student.student_management.dto.ApiResponse;
import com.student.student_management.dto.CreateAndUpdateScore;
import com.student.student_management.model.ScoreModel;
import com.student.student_management.service.ScoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/v1/score")
@RequiredArgsConstructor
public class ScoreController {
    private final ScoreService scoreService;


    @PostMapping("/single")
    public ResponseEntity<ApiResponse<ScoreModel>> createOneScore(@Valid @RequestBody CreateAndUpdateScore body){
        ApiResponse<ScoreModel> response = scoreService.createOneScore(body);
        return new ResponseEntity<>(response,response.httpStatus());
    }
    @PostMapping("/multiple")
    public ResponseEntity<ApiResponse<List<ScoreModel>>> createMultipleScore(@Valid @RequestParam("file") MultipartFile body) throws IOException {
        InputStream inputStream = body.getInputStream();
        ApiResponse<List<ScoreModel>> response = scoreService.createMultipleScore(inputStream);
        return new ResponseEntity<>(response,response.httpStatus());
    }
    @PutMapping("single/{id}")
    public ResponseEntity<ApiResponse<ScoreModel>> updateOneScore(@PathVariable Long id, @Valid @RequestBody CreateAndUpdateScore body){
        ApiResponse<ScoreModel> response = scoreService.updateOneScore(id, body);
        return new ResponseEntity<>(response,response.httpStatus());
    }
}
