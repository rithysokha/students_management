package com.student.student_management.controller.android;

import com.student.student_management.dto.ApiResponse;
import com.student.student_management.dto.Status;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/greeting")
public class GreetingController {
    @GetMapping
    public ResponseEntity<ApiResponse<String>> greeting() {
        String greet;
        int hour = LocalDateTime.now().getHour();

        if (hour >= 5 && hour < 12) {
            greet = "Good morning";
        } else if (hour >= 12 && hour < 17) {
            greet = "Good afternoon";
        } else if (hour >= 17 && hour < 21) {
            greet = "Good evening";
        } else {
            greet = "Good night";
        }

        ApiResponse<String> response = new ApiResponse<>("", greet + " user!", HttpStatus.OK, Status.SUCCESS);
        return ResponseEntity.ok(response);
    }
}