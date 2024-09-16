package com.student.student_management.config;

import com.student.student_management.model.UserModel;
import com.student.student_management.repository.UserRepository;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
@RequiredArgsConstructor
public class UserConfig {

    private final UserRepository userRepository;
    private final PasswordEncoderConfig passwordEncoder;

    @Bean
    CommandLineRunner createDefaultUser() {
        Dotenv dotenv = Dotenv.load();
        return _ -> {
            if (userRepository.findByUsername(dotenv.get("DEFAULT_USER")).isEmpty()) {
                UserModel defaultUser = new UserModel();
                defaultUser.setUsername(dotenv.get("DEFAULT_USER"));
                defaultUser.setPassword(passwordEncoder.passwordEncoder().encode(dotenv.get("DEFAULT_USER_PASSWORD")));
                defaultUser.setCreatedAt(LocalDateTime.now());
                userRepository.save(defaultUser);
            }
        };
    }
}