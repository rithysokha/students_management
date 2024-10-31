package com.student.student_management.controller.android;

import com.student.student_management.dto.ApiResponse;
import com.student.student_management.dto.Status;
import com.student.student_management.model.History;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/v1/history")
public class HistoryController {

    @GetMapping
    public ResponseEntity<ApiResponse<List<History>>> GetHistory() {
        List<History> histories = Arrays.asList(
                new History(1, "John Doe", "Smile", "2024-09-01T12:15:30"),
                new History(2, "Jane Smith", "Sad", "2024-09-01T10:15:30"),
                new History(3, "Alice Johnson", "Angry", "2024-09-01T10:15:30")
        );
        
        histories.sort(Comparator.comparing(History::getCreated_at));

        ApiResponse<List<History>> response = new ApiResponse<>("Get All History Successfully", histories, HttpStatus.OK, Status.SUCCESS);
        return ResponseEntity.ok(response);
    }
}
