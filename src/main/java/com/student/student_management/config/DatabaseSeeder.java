package com.student.student_management.config;

import com.student.student_management.model.*;
import com.student.student_management.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DatabaseSeeder {
    private final FacultyRepository facultyRepository;
    private final DepartmentRepository departmentRepository;
    private final MajorRepository majorRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
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
                defaultUser.setRole(Role.ADMIN);
                defaultUser.setCreatedAt(LocalDateTime.now());
                userRepository.save(defaultUser);
            }
            if(facultyRepository.findByIdAndDeletedAtIsNull(1L).isEmpty()){
                FacultyModel defaultFaculty = new FacultyModel();
                defaultFaculty.setFacultyName("Faculty fo Engineering");
                facultyRepository.save(defaultFaculty);
            }
            if(departmentRepository.findById(1L).isEmpty()){
                DepartmentModel defaultDepartment = new DepartmentModel();
                defaultDepartment.setDepartmentName("ITE");
                departmentRepository.save(defaultDepartment);
            }
            if(majorRepository.findById(1L).isEmpty()){
                MajorModel defaultMajor = new MajorModel();
                defaultMajor.setMajorName("ITE");
                majorRepository.save(defaultMajor);
            }
            if(courseRepository.findAllById(List.of(1L,2L)).isEmpty()){
                List<CourseModel> defaultCourse = new ArrayList<>();
                defaultCourse.add(new CourseModel(LocalDate.now(),LocalDate.now().plusMonths(6),"OOAD", 4.0f ));
                defaultCourse.add(new CourseModel(LocalDate.now(),LocalDate.now().plusMonths(6),"DSA", 4.0f ));
            courseRepository.saveAll(defaultCourse);
            }
        };
    }
}