package com.student.student_management.controller;

import com.student.student_management.dto.ApiResponse;
import com.student.student_management.dto.Login;
import com.student.student_management.dto.Register;
import com.student.student_management.dto.Token;
import com.student.student_management.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<String>> register(@RequestBody Register registerBody) {
        ApiResponse<String> response = authService.register(registerBody);
        return new ResponseEntity<>(response, response.httpStatus());
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Token>> login(@RequestBody Login loginBody) {
        ApiResponse<Token> response = authService.login(loginBody);
        return new ResponseEntity<>(response, response.httpStatus());
    }

    @PostMapping("/validate")
    public ResponseEntity<Void> validateAuth() {
        return ResponseEntity.ok().build();
    }
}
