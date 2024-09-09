package com.student.student_management.service;

import com.student.student_management.dto.ApiResponse;
import com.student.student_management.model.ClassModel;
import com.student.student_management.repository.ClassRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ClassService {
    private final ClassRepository classRepository;

    
    public ApiResponse<List<ClassModel>> getAllClasses() {
        return new ApiResponse<>("All classes", classRepository.findAllByDeletedAtIsNull(), HttpStatus.OK);
    }

    public ApiResponse<ClassModel> getOneClassById(Long id) {
        Optional<ClassModel> classOptional = classRepository.findById(id);
        return classOptional.map(classModel
                -> new ApiResponse<>("Class found", classModel, HttpStatus.OK)).orElseGet(()
                -> new ApiResponse<>("Class not found", null, HttpStatus.NOT_FOUND));
    }

    public ApiResponse<ClassModel> createClass(ClassModel classBody) {
        ClassModel classRes;
        try {
            classBody.setCreatedAt(LocalDateTime.now());
            classRes = classRepository.save(classBody);
        }catch (Exception e){
            return new ApiResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
        }
        return new ApiResponse<>("New class created", classRes , HttpStatus.CREATED);
    }

    public ApiResponse<ClassModel> deleteClassById(Long id) {
        ApiResponse<ClassModel> classResponse = getOneClassById(id);
        if (classResponse.status() != HttpStatus.OK) {
            return classResponse;
        }
        ClassModel classRes = classResponse.data();
        classRes.setDeletedAt(LocalDateTime.now());
        classRepository.save(classRes);
        return new ApiResponse<>("Class with id " + id + " is deleted", classRes, HttpStatus.OK);
    }

    @Transactional
    public ApiResponse<ClassModel> updateClassById(Long id, ClassModel classBody) {
        ApiResponse<ClassModel> classResponse = getOneClassById(id);
        if (classResponse.status() != HttpStatus.OK) {
            return classResponse;
        }
        ClassModel classRes = classResponse.data();
        if (classBody.getClassName() != null && !classBody.getClassName().isEmpty()) {
            classRes.setClassName(classBody.getClassName());
        }
        if (classBody.getDepartmentId() != null) {
            classRes.setDepartmentId(classBody.getDepartmentId());
        }
        classRes.setUpdatedAt(LocalDateTime.now());
        classRepository.save(classRes);
        return new ApiResponse<>("Class updated", classRes, HttpStatus.OK);
    }
}
