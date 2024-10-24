package com.student.student_management.repository;

import com.student.student_management.model.FacultyModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FacultyRepository extends JpaRepository<FacultyModel, Long> {
    List<FacultyModel> findAllByDeletedAtIsNull();

    Optional<FacultyModel> findByIdAndDeletedAtIsNull(Long id);
    Optional<FacultyModel> findByFacultyName(String facultyName);
}
