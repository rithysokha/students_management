package com.student.student_management.repository;

import com.student.student_management.model.DepartmentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentModel, Long> {

    List<DepartmentModel> findAllByDeletedAtIsNull();

    Boolean existsByDepartmentName(String departmentName);
}
