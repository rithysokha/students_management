package com.student.student_management.service;

import com.student.student_management.dto.ApiResponse;
import com.student.student_management.dto.CreateAndUpdateDepartment;
import com.student.student_management.dto.Status;
import com.student.student_management.model.DepartmentModel;
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
public class DepartmentService {
    private final DepartmentRepository departmentRepository;


    public ApiResponse<List<DepartmentModel>> getAllDepartments() {
        try {
        return new ApiResponse<>("All department", departmentRepository.findAllByDeletedAtIsNull(), HttpStatus.OK, Status.SUCCESS);
        } catch (RuntimeException e) {
            return new ApiResponse<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR, Status.FAIL);
        }
    }

    public ApiResponse<DepartmentModel> getOneDepartmentById(Long id) {
        try {
        Optional<DepartmentModel> departmentOptional = departmentRepository.findById(id);
        if (departmentOptional.isPresent() && departmentOptional.get().getDeletedAt() == null){
            return new ApiResponse<>("Department found", departmentOptional.get(), HttpStatus.OK, Status.SUCCESS);
        }
        return new ApiResponse<>("Department not found", null, HttpStatus.NOT_FOUND, Status.FAIL);
        } catch (RuntimeException e) {
            return new ApiResponse<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR, Status.FAIL);
        }
    }

    public ApiResponse<DepartmentModel> createDepartment(CreateAndUpdateDepartment departmentBody) {
        try{
            DepartmentModel departmentModel = new DepartmentModel();
            departmentModel.setCreatedAt(LocalDateTime.now());
            departmentModel.setDepartmentName(departmentBody.departmentName());
            DepartmentModel response = departmentRepository.save(departmentModel);
            return new ApiResponse<>("New Department created", response, HttpStatus.OK, Status.SUCCESS);
        }catch (Exception e){
            return new ApiResponse<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR, Status.FAIL);
        }
    }

    public ApiResponse<DepartmentModel> deleteDepartmentById(Long id) {
        try {
        ApiResponse<DepartmentModel> departmentResponse = getOneDepartmentById(id);
        if(departmentResponse.httpStatus() != HttpStatus.OK){
            return departmentResponse;
        }
        DepartmentModel departmentData = departmentResponse.data();
        departmentData.setDeletedAt(LocalDateTime.now());
        departmentRepository.save(departmentData);
        return new ApiResponse<>("Department deleted", null, HttpStatus.OK, Status.SUCCESS);
        } catch (Exception e) {
            return new ApiResponse<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR, Status.FAIL);
        }
    }

    @Transactional
    public ApiResponse<DepartmentModel> updateDepartmentById(Long id, CreateAndUpdateDepartment departmentBody) {
        try {
            ApiResponse<DepartmentModel> departmentResponse = getOneDepartmentById(id);
            if(departmentResponse.httpStatus() != HttpStatus.OK){
                return departmentResponse;
            }
            DepartmentModel departmentData = departmentResponse.data();

            if (departmentBody.departmentName() != null && !departmentBody.departmentName().isEmpty()) {
                departmentData.setDepartmentName(departmentBody.departmentName());
            }
            departmentData.setUpdatedAt(LocalDateTime.now());
            departmentRepository.save(departmentData);
            return new ApiResponse<>("Department updated", departmentData, HttpStatus.OK, Status.SUCCESS);
        }catch (Exception e){
            return new ApiResponse<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR, Status.FAIL);
        }
    }
}