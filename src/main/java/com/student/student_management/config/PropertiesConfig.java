package com.student.student_management.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "student")
public record PropertiesConfig(String jwtSecret, String defaultUsername, String defaultPassword) {
}
