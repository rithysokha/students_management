package com.student.student_management.repository;

import com.student.student_management.model.ScoreModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreRepository extends JpaRepository<ScoreModel, Long> {
}
