package com.student.student_management.service;

import com.student.student_management.dto.ApiResponse;
import com.student.student_management.dto.CreateAndUpdateDepartment;
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

    private Optional<DepartmentModel> findDepartmentById(Long id){
        return departmentRepository.findById(id);
    }

    public ApiResponse<List<DepartmentModel>> getAllDepartments() {
        return new ApiResponse<>("All department", departmentRepository.findAllByDeletedAtIsNull(), HttpStatus.OK);
    }

    public ApiResponse<DepartmentModel> getOneDepartmentById(Long id) {
        Optional<DepartmentModel> departmentOptional = findDepartmentById(id);
        return departmentOptional.map(departmentModel
                -> new ApiResponse<>("Department found", departmentModel, HttpStatus.OK)).orElseGet(()
                -> new ApiResponse<>("Department not found", null, HttpStatus.NOT_FOUND));
    }

    public ApiResponse<DepartmentModel> createDepartment(CreateAndUpdateDepartment departmentBody) {
        try{
            DepartmentModel departmentModel = new DepartmentModel();
            departmentModel.setCreatedAt(LocalDateTime.now());
            departmentModel.setDepartmentName(departmentBody.departmentName());
            DepartmentModel response = departmentRepository.save(departmentModel);
            return new ApiResponse<>("New Department created", response, HttpStatus.OK);
        }catch (Exception e){
            return new ApiResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
        }
    }

    public ApiResponse<String> deleteDepartmentById(Long id) {
        Optional<DepartmentModel> departmentOptional = findDepartmentById(id);
        if(departmentOptional.isEmpty()) return new ApiResponse<>("Department not found", null, HttpStatus.NOT_FOUND);
        DepartmentModel departmentRes = departmentOptional.get();
        departmentRes.setDeletedAt(LocalDateTime.now());
        departmentRepository.save(departmentRes);
        return new ApiResponse<>("Department deleted", null, HttpStatus.OK);
    }

    @Transactional
    public ApiResponse<DepartmentModel> updateDepartmentById(Long id, CreateAndUpdateDepartment departmentBody) {
        try {
            Optional<DepartmentModel> departmentOptional = findDepartmentById(id);
            if (departmentOptional.isEmpty())
                return new ApiResponse<>("Department not found", null, HttpStatus.NOT_FOUND);
            DepartmentModel departmentRes = departmentOptional.get();

            if (departmentBody.departmentName() != null && !departmentBody.departmentName().isEmpty()) {
                departmentRes.setDepartmentName(departmentBody.departmentName());
            }
            departmentRes.setUpdatedAt(LocalDateTime.now());
            departmentRepository.save(departmentRes);
            return new ApiResponse<>("Department updated", departmentRes, HttpStatus.OK);
        }catch (Exception e){
            return new ApiResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
        }
    }
}
