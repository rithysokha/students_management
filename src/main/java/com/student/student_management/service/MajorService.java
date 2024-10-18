package com.student.student_management.service;

import com.student.student_management.dto.ApiResponse;
import com.student.student_management.dto.CreateAndUpdateMajor;
import com.student.student_management.dto.Status;
import com.student.student_management.model.MajorModel;
import com.student.student_management.model.DepartmentModel;
import com.student.student_management.repository.MajorRepository;
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
public class MajorService {
    private final MajorRepository majorRepository;
    private final DepartmentRepository departmentRepository;


    public ApiResponse<List<MajorModel>> getAllMajors() {
        try {
            return new ApiResponse<>("All majors", majorRepository.findAllByDeletedAtIsNull(), HttpStatus.OK, Status.SUCCESS);
        } catch (RuntimeException e) {
            return new ApiResponse<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR, Status.FAIL);
        }
    }

    public ApiResponse<MajorModel> getOneMajorById(Long id) {
        try {
            Optional<MajorModel> classOptional = majorRepository.findById(id);
            if (classOptional.isPresent() && classOptional.get().getDeletedAt() == null) {
                return new ApiResponse<>("Major found", classOptional.get(), HttpStatus.OK, Status.SUCCESS);
            }
            return new ApiResponse<>("Major not found", null, HttpStatus.NOT_FOUND, Status.FAIL);
        } catch (RuntimeException e) {
            return new ApiResponse<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR, Status.FAIL);
        }
    }

    public ApiResponse<MajorModel> createMajor(CreateAndUpdateMajor classBody) {
        try {
            if (majorRepository.existsByMajorName(classBody.majorName())) {
                return new ApiResponse<>("Major name already taken", null, HttpStatus.CONFLICT, Status.FAIL);
            }
            MajorModel majorModel = new MajorModel();
            Optional<DepartmentModel> departmentOptional = departmentRepository.findById(classBody.departmentId());
            if (departmentOptional.isEmpty())
                return new ApiResponse<>("Department not found", null, HttpStatus.NOT_FOUND, Status.FAIL);
            majorModel.setDepartment(departmentOptional.get());
            majorModel.setMajorName(classBody.majorName());
            majorModel.setCreatedAt(LocalDateTime.now());
            MajorModel response = majorRepository.save(majorModel);
            return new ApiResponse<>("New major created", response, HttpStatus.CREATED, Status.SUCCESS);
        } catch (RuntimeException e) {
            return new ApiResponse<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR, Status.FAIL);
        }
    }

    public ApiResponse<MajorModel> deleteMajorById(Long id) {
        try {
            ApiResponse<MajorModel> classResponse = getOneMajorById(id);
            if (classResponse.httpStatus() != HttpStatus.OK) {
                return classResponse;
            }
            MajorModel classData = classResponse.data();
            classData.setDeletedAt(LocalDateTime.now());
            majorRepository.save(classData);
            return new ApiResponse<>("Major with id " + id + " is deleted", classData, HttpStatus.OK, Status.SUCCESS);
        } catch (Exception e) {
            return new ApiResponse<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR, Status.FAIL);
        }
    }

    @Transactional
    public ApiResponse<MajorModel> updateClassById(Long id, CreateAndUpdateMajor classBody) {
        try {
            if (majorRepository.existsByMajorName(classBody.majorName())) {
                return new ApiResponse<>("Class name already taken", null, HttpStatus.CONFLICT, Status.FAIL);
            }
            ApiResponse<MajorModel> classResponse = getOneMajorById(id);
            if (classResponse.httpStatus() != HttpStatus.OK) {
                return classResponse;
            }
            MajorModel classData = classResponse.data();

            if (classBody.majorName() != null && !classBody.majorName().isEmpty()) {
                classData.setMajorName(classBody.majorName());
            }
            if (classBody.departmentId() != null) {
                Optional<DepartmentModel> departmentOptional = departmentRepository.findById(classBody.departmentId());
                if (departmentOptional.isEmpty() || departmentOptional.get().getDeletedAt() != null)
                    return new ApiResponse<>("Department not found", null, HttpStatus.NOT_FOUND, Status.FAIL);
                classData.setDepartment(departmentOptional.get());
            }
            classData.setUpdatedAt(LocalDateTime.now());
            majorRepository.save(classData);
            return new ApiResponse<>("Class updated", classData, HttpStatus.OK, Status.SUCCESS);
        } catch (Exception e) {
            return new ApiResponse<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR, Status.FAIL);
        }
    }

    public ApiResponse<List<MajorModel>> getClassesByDepartmentId(Long departmentId) {
        try {
            return new ApiResponse<>("Classes by department", majorRepository.findAllByDepartmentIdAndDeletedAtIsNull(departmentId), HttpStatus.OK, Status.SUCCESS);
        } catch (RuntimeException e) {
            return new ApiResponse<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR, Status.FAIL);
        }
    }
}
