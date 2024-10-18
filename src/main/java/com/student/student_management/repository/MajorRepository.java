package com.student.student_management.repository;

import com.student.student_management.model.MajorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MajorRepository extends JpaRepository<MajorModel, Long> {

    List<MajorModel> findAllByDeletedAtIsNull();

    List<MajorModel> findAllByDepartmentIdAndDeletedAtIsNull(Long departmentId);

    Boolean existsByMajorName(String className);
}
