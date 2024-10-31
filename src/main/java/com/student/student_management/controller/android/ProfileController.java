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
                new Profile(1, "John Doe", "john.doe@example.com", "imgUrl1", "Phnom Penh", "012959675"),
                new Profile(2, "Jane Smith", "jane.smith@example.com", "imgUrl2", "Phnom Penh", "012959675"),
                new Profile(3, "Alice Johnson", "alice.johnson@example.com", "imgUrl3", "Phnom Penh", "012959675")
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
