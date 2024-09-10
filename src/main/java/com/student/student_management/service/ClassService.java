package com.student.student_management.service;

import com.student.student_management.dto.ApiResponse;
import com.student.student_management.dto.CreateAndUpdateClass;
import com.student.student_management.model.ClassModel;
import com.student.student_management.model.DepartmentModel;
import com.student.student_management.repository.ClassRepository;
import com.student.student_management.repository.DepartmentRepository;
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
    private final DepartmentRepository departmentRepository;

    
    public ApiResponse<List<ClassModel>> getAllClasses() {
        return new ApiResponse<>("All classes", classRepository.findAllByDeletedAtIsNull(), HttpStatus.OK);
    }

    public ApiResponse<ClassModel> getOneClassById(Long id) {
        Optional<ClassModel> classOptional = classRepository.findById(id);
        return classOptional.map(classModel
                -> new ApiResponse<>("Class found", classModel, HttpStatus.OK)).orElseGet(()
                -> new ApiResponse<>("Class not found", null, HttpStatus.NOT_FOUND));
    }

    public ApiResponse<ClassModel> createClass(CreateAndUpdateClass classBody) {
        try {
            ClassModel classModel = new ClassModel();
            classModel.setClassName(classBody.className());
            classModel.setDepartmentId(classBody.departmentId());
            classModel.setCreatedAt(LocalDateTime.now());
            ClassModel response = classRepository.save(classModel);
            return new ApiResponse<>("New class created", response , HttpStatus.CREATED);
        }catch (Exception e){
            return new ApiResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
        }
    }

    public ApiResponse<ClassModel> deleteClassById(Long id) {
        ApiResponse<ClassModel> classResponse = getOneClassById(id);
        if (classResponse.status() != HttpStatus.OK) {
            return classResponse;
        }
        ClassModel classData = classResponse.data();
        classData.setDeletedAt(LocalDateTime.now());
        classRepository.save(classData);
        return new ApiResponse<>("Class with id " + id + " is deleted", classData, HttpStatus.OK);
    }

    @Transactional
    public ApiResponse<ClassModel> updateClassById(Long id, CreateAndUpdateClass classBody) {

        ApiResponse<ClassModel> classResponse = getOneClassById(id);
        if (classResponse.status() != HttpStatus.OK) {
            return classResponse;
        }
        ClassModel classData = classResponse.data();

            if (classBody.className() != null && !classBody.className().isEmpty()) {
                classData.setClassName(classBody.className());
            }
            if (classBody.departmentId() != null) {
                Optional<DepartmentModel> departmentOptional = departmentRepository.findById(classBody.departmentId());
                if(departmentOptional.isEmpty())
                    return new ApiResponse<>("Department not found", null, HttpStatus.NOT_FOUND);
                classData.setDepartmentId(classBody.departmentId());
            }
            classData.setUpdatedAt(LocalDateTime.now());
            classRepository.save(classData);
            return new ApiResponse<>("Class updated", classData, HttpStatus.OK);
    }
}
