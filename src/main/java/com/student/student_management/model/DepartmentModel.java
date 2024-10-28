package com.student.student_management.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity(name = "department")
@NoArgsConstructor
public class DepartmentModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "department_name", length = 20, nullable = false, unique = true)
    private String departmentName;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    @JsonManagedReference
    @JsonIgnore
    private List<MajorModel> classes;
    @ManyToOne
    @JoinColumn(name = "faculty_id", referencedColumnName = "id")
    @JsonIgnore
    private FacultyModel faculty;
    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public DepartmentModel(String departmentName, FacultyModel faculty) {
        this.departmentName = departmentName;
        this.faculty = faculty;
    }
}
