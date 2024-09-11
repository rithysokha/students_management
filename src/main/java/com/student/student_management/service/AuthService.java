package com.student.student_management.service;

import com.student.student_management.config.PasswordEncoderConfig;
import com.student.student_management.dto.ApiResponse;
import com.student.student_management.dto.RegisterAndLogin;
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
    public ApiResponse<Token> register(RegisterAndLogin registerBody) {
        var user = userRepository.findByUsername(registerBody.username());
        if(user.isPresent()){
            return new ApiResponse<>("user already exist", null, HttpStatus.BAD_REQUEST);
        }
        UserModel newUser = new UserModel();
        newUser.setUsername(registerBody.username());
        newUser.setPassword(passwordEncoder.passwordEncoder().encode(registerBody.password()));
        newUser.setCreatedAt(LocalDateTime.now());
        userRepository.save(newUser);
        Token token = new Token(
                getToken(newUser.getUsername(), "access"),
                getToken(newUser.getUsername(), "refresh"));
        return new ApiResponse<>("User registered successfully", token, HttpStatus.CREATED);
    }

    public ApiResponse<Token> login(RegisterAndLogin loginBody) {
    return null;
    }

    public ApiResponse<Token> getRefreshToken(String refreshToken) {
        return null;
    }
    private String getToken(String username, String type) {
        return jwtService.generateToken(userService.loadUserByUsername(username), type);
    }
}
