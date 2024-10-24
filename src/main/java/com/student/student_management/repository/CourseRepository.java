package com.student.student_management.repository;

import com.student.student_management.model.CourseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<CourseModel, Long> {
    List<CourseModel> findAllByDeletedAtIsNull();

    Optional<CourseModel> findByIdAndDeletedAtIsNull(Long id);
}
