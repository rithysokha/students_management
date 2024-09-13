package com.student.student_management.repository;

import com.student.student_management.model.ClassModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassRepository extends JpaRepository<ClassModel, Long> {

    List<ClassModel> findAllByDeletedAtIsNull();
    List<ClassModel> findAllByDepartmentIdAndDeletedAtIsNull(Long departmentId);
}
