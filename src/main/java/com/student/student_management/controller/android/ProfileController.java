package com.student.student_management.controller.android;

import com.student.student_management.dto.ApiResponse;
import com.student.student_management.dto.Status;
import com.student.student_management.model.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/profiles")
public class ProfileController {

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Profile>> GetProfileDetail(@PathVariable String id) {
        List<Profile> profiles = Arrays.asList(
                new Profile(1, "John Doe", "john.doe@example.com", "2001-09-09","https://letsenhance.io/static/8f5e523ee6b2479e26ecc91b9c25261e/1015f/MainAfter.jpg", "Phnom Penh", "012959675"),
                new Profile(2, "Jane Smith", "jane.smith@example.com", "2002-10-09", "https://img.freepik.com/premium-photo/stylish-man-flat-vector-profile-picture-ai-generated_606187-310.jpg?semt=ais_hybrid", "Phnom Penh", "012959675"),
                new Profile(3, "Alice Johnson", "alice.johnson@example.com","2003-11-09","https://cdn3.pixelcut.app/1/3/profile_picture_1728ecf2bd.jpg", "Phnom Penh", "012959675")
        );

        Profile profile = profiles.stream().filter(p -> p.getId() == Integer.parseInt(id)).findFirst().orElse(null);
        if (profile == null) {
            ApiResponse<Profile> response = new ApiResponse<>("Profile not found", null, HttpStatus.NOT_FOUND, Status.FAIL);
            return ResponseEntity.ok(response);
        }

        ApiResponse<Profile> response = new ApiResponse<>("Retrived Successfuly", profile, HttpStatus.OK, Status.SUCCESS);
        return ResponseEntity.ok(response);
    }
}
