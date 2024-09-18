package com.student.student_management.service;

import com.student.student_management.dto.ApiResponse;
import com.student.student_management.dto.CreateAndUpdateClass;
import com.student.student_management.dto.Status;
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
        return new ApiResponse<>("All classes", classRepository.findAllByDeletedAtIsNull(), HttpStatus.OK, Status.SUCCESS);
    }

    public ApiResponse<ClassModel> getOneClassById(Long id) {
        Optional<ClassModel> classOptional = classRepository.findById(id);
        if(classOptional.isPresent() && classOptional.get().getDeletedAt() == null){
            return new ApiResponse<>("Class found", classOptional.get(), HttpStatus.OK, Status.SUCCESS);
        }
        return  new ApiResponse<>("Class not found", null, HttpStatus.NOT_FOUND, Status.FAIL);
    }

    public ApiResponse<ClassModel> createClass(CreateAndUpdateClass classBody) {
        try {
            ClassModel classModel = new ClassModel();
            Optional<DepartmentModel> departmentOptional = departmentRepository.findById(classBody.departmentId());
            if (departmentOptional.isEmpty())
                return new ApiResponse<>("Department not found", null, HttpStatus.NOT_FOUND, Status.FAIL);
            classModel.setDepartment(departmentOptional.get());
            classModel.setClassName(classBody.className());
            classModel.setCreatedAt(LocalDateTime.now());
            ClassModel response = classRepository.save(classModel);
            return new ApiResponse<>("New class created", response , HttpStatus.CREATED, Status.SUCCESS);
        }catch (Exception e){
            return new ApiResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST, Status.FAIL);
        }
    }

    public ApiResponse<ClassModel> deleteClassById(Long id) {
        try {
            ApiResponse<ClassModel> classResponse = getOneClassById(id);
            if (classResponse.httpStatus() != HttpStatus.OK) {
                return classResponse;
            }
            ClassModel classData = classResponse.data();
            classData.setDeletedAt(LocalDateTime.now());
            classRepository.save(classData);
            return new ApiResponse<>("Class with id " + id + " is deleted", classData, HttpStatus.OK, Status.SUCCESS);
        }catch (Exception e){
            return new ApiResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST, Status.FAIL);
        }
    }

    @Transactional
    public ApiResponse<ClassModel> updateClassById(Long id, CreateAndUpdateClass classBody) {
        try {
            ApiResponse<ClassModel> classResponse = getOneClassById(id);
            if (classResponse.httpStatus() != HttpStatus.OK) {
                return classResponse;
            }
            ClassModel classData = classResponse.data();

            if (classBody.className() != null && !classBody.className().isEmpty()) {
                classData.setClassName(classBody.className());
            }
            if (classBody.departmentId() != null) {
                Optional<DepartmentModel> departmentOptional = departmentRepository.findById(classBody.departmentId());
                if (departmentOptional.isEmpty() || departmentOptional.get().getDeletedAt() !=null)
                    return new ApiResponse<>("Department not found", null, HttpStatus.NOT_FOUND, Status.FAIL);
                classData.setDepartment(departmentOptional.get());
            }
            classData.setUpdatedAt(LocalDateTime.now());
            classRepository.save(classData);
            return new ApiResponse<>("Class updated", classData, HttpStatus.OK, Status.SUCCESS);
        } catch (Exception e) {
            return new ApiResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST, Status.FAIL);
        }
    }

    public ApiResponse<List<ClassModel>> getClassesByDepartmentId(Long departmentId) {
        return new ApiResponse<>("Classes by department", classRepository.findAllByDepartmentIdAndDeletedAtIsNull(departmentId), HttpStatus.OK, Status.SUCCESS);
    }
}
