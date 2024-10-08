package com.student.student_management.controller;

import com.student.student_management.dto.ApiResponse;
import com.student.student_management.dto.RegisterAndLogin;
import com.student.student_management.dto.Token;
import com.student.student_management.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ApiResponse<String>> register(@RequestBody RegisterAndLogin registerBody) {
        ApiResponse<String> response = authService.register(registerBody);
        return new ResponseEntity<>(response, response.httpStatus());
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Token>> login(@RequestBody RegisterAndLogin loginBody) {
        ApiResponse<Token> response = authService.login(loginBody);
        return new ResponseEntity<>(response, response.httpStatus());
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<Token>> refreshToken(HttpServletRequest request) {
        String refreshToken = request.getHeader("Refresh-Token");
        ApiResponse<Token> response = authService.getRefreshToken(refreshToken);
        return new ResponseEntity<>(response, response.httpStatus());
    }

}
