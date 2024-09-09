package com.student.student_management.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@Entity(name = "class")
public class ClassModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "class_name", length = 20, nullable = false, unique = true)
    private String className;
    @Column(name = "department_id")
    @JsonBackReference
    private Long departmentId;
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime udatedAt;
    @Column(name = "deletd_at")
    private LocalDateTime deletedAt;
}
