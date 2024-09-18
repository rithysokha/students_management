package com.student.student_management.repository;

import com.student.student_management.model.StudentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<StudentModel, Long> {
    List<StudentModel> findAllByDeletedAtIsNull();

    List<StudentModel> findAllByStudentClassIdAndDeletedAtIsNull(Long classId);

    List<StudentModel> findAllByStudentClassDepartmentIdAndDeletedAtIsNull(Long departmentId);

    Boolean existsByPhoneNumber(String phoneNumber);
}