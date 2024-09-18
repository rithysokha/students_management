package com.student.student_management.service;

import com.student.student_management.config.PasswordEncoderConfig;
import com.student.student_management.dto.ApiResponse;
import com.student.student_management.dto.RegisterAndLogin;
import com.student.student_management.dto.Status;
import com.student.student_management.dto.Token;
import com.student.student_management.model.UserModel;
import com.student.student_management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoderConfig passwordEncoder;
    private final UserService userService;
    private final JwtService jwtService;

    public ApiResponse<String> register(RegisterAndLogin registerBody) {
        try {
            var user = userRepository.findByUsername(registerBody.username());
            if (user.isPresent()) {
                return new ApiResponse<>("user already exist", null, HttpStatus.BAD_REQUEST, Status.FAIL);
            }
            UserModel newUser = new UserModel();
            newUser.setUsername(registerBody.username());
            newUser.setPassword(passwordEncoder.passwordEncoder().encode(registerBody.password()));
            newUser.setCreatedAt(LocalDateTime.now());
            userRepository.save(newUser);

            return new ApiResponse<>("User registered successfully", "Please use the credentials to login", HttpStatus.CREATED, Status.SUCCESS);
        } catch (RuntimeException e) {
            return new ApiResponse<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR, Status.FAIL);
        }
    }

    public ApiResponse<Token> login(RegisterAndLogin loginBody) {
        try {
            var user = userRepository.findByUsername(loginBody.username());
            if (user.isPresent()) {
                if (passwordEncoder.passwordEncoder().matches(loginBody.password(), user.get().getPassword())) {
                    Token token = new Token(
                            getToken(user.get().getUsername(), "access"),
                            getToken(user.get().getUsername(), "refresh"));
                    return new ApiResponse<>("Login successful", token, HttpStatus.OK, Status.SUCCESS);
                }
            }
            return new ApiResponse<>("Invalid credentials", null, HttpStatus.UNAUTHORIZED, Status.FAIL);
        } catch (RuntimeException e) {
            return new ApiResponse<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR, Status.FAIL);
        }

    }

    public ApiResponse<Token> getRefreshToken(String refreshToken) {
        try {
            String newAccessToken = jwtService.generateToken(userService.loadUserByUsername(jwtService.extractUsername(refreshToken)), "access");
            String newRefreshToken = jwtService.generateToken(userService.loadUserByUsername(jwtService.extractUsername(refreshToken)), "refresh");
            Token token = new Token(newAccessToken, newRefreshToken);
            return new ApiResponse<>("Token refreshed", token, HttpStatus.OK, Status.SUCCESS);
        } catch (RuntimeException e) {
            return new ApiResponse<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR, Status.FAIL);
        }
    }

    private String getToken(String username, String type) {
        try {
            return jwtService.generateToken(userService.loadUserByUsername(username), type);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
