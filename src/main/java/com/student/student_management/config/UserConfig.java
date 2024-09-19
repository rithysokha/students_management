package com.student.student_management.config;

import com.student.student_management.model.UserModel;
import com.student.student_management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
@RequiredArgsConstructor
public class UserConfig {

    private final UserRepository userRepository;
    private final PasswordEncoderConfig passwordEncoder;
    @Value("${student.default-username}")
    private String defaultUsername;
    @Value("${student.default-password}")
    private String defaultPassword;

    @Bean
    CommandLineRunner createDefaultUser() {


        return _ -> {
            if (userRepository.findByUsername(defaultUsername).isEmpty()) {
                UserModel defaultUser = new UserModel();
                defaultUser.setUsername(defaultUsername);
                defaultUser.setPassword(passwordEncoder.passwordEncoder().encode(defaultPassword));
                defaultUser.setCreatedAt(LocalDateTime.now());
                userRepository.save(defaultUser);
            }
        };
    }
}