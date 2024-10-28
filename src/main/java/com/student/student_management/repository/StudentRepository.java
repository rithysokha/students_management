package com.student.student_management.repository;

import com.student.student_management.model.StudentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<StudentModel, Long> {
    List<StudentModel> findAllByDeletedAtIsNull();

    List<StudentModel> findAllByStudentMajorIdAndDeletedAtIsNull(Long majorId);

    List<StudentModel> findAllByStudentMajorDepartmentIdAndDeletedAtIsNull(Long departmentId);

    Boolean existsByPhoneNumber(String phoneNumber);

    Optional<StudentModel> findByEmail(String email);
}