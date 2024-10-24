package com.student.student_management.service;

import com.student.student_management.dto.ApiResponse;
import com.student.student_management.dto.CreateAndUpdateStudent;
import com.student.student_management.dto.Status;
import com.student.student_management.model.MajorModel;
import com.student.student_management.model.StudentModel;
import com.student.student_management.repository.MajorRepository;
import com.student.student_management.repository.StudentRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final MajorRepository majorRepository;
    private final ExcelService excelService;

    public ApiResponse<List<StudentModel>> getAllStudents() {
        try {
            return new ApiResponse<>("All students", studentRepository.findAllByDeletedAtIsNull(), HttpStatus.OK, Status.SUCCESS);
        } catch (RuntimeException e) {
            return new ApiResponse<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR, Status.FAIL);
        }
    }

    public ApiResponse<StudentModel> getOneStudentById(Long id) {
        try {
            Optional<StudentModel> studenOptional = studentRepository.findById(id);
            if (studenOptional.isPresent() && studenOptional.get().getDeletedAt() == null) {
                return new ApiResponse<>("Student found", studenOptional.get(), HttpStatus.OK, Status.SUCCESS);
            }
            return new ApiResponse<>("Student not found", null, HttpStatus.NOT_FOUND, Status.FAIL);
        } catch (RuntimeException e) {
            return new ApiResponse<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR, Status.FAIL);
        }
    }

    public ApiResponse<StudentModel> createStudent(CreateAndUpdateStudent studentBody) {
        try {
            if (studentRepository.existsByPhoneNumber(studentBody.phoneNumber())) {
                return new ApiResponse<>("Phone number already taken", null, HttpStatus.CONFLICT, Status.FAIL);
            }
            StudentModel studentModel = new StudentModel();
            Optional<MajorModel> classOptional = majorRepository.findById(studentBody.classId());
            studentModel.setFirstName(studentBody.firstName());
            studentModel.setLastName(studentBody.lastName());
            studentModel.setDateOfBirth(studentBody.dateOfBirth());
            studentModel.setAddress(studentBody.address());
            if (classOptional.isEmpty() || classOptional.get().getDeletedAt() != null)
                return new ApiResponse<>("Class not found", null, HttpStatus.NOT_FOUND, Status.FAIL);
            studentModel.setStudentMajor(classOptional.get());
            studentModel.setPhoneNumber(studentBody.phoneNumber());
            StudentModel response = studentRepository.save(studentModel);
            return new ApiResponse<>("Student created", response, HttpStatus.CREATED, Status.SUCCESS);
        } catch (RuntimeException e) {
            return new ApiResponse<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR, Status.FAIL);
        }
    }

    public ApiResponse<StudentModel> deleteStudentById(Long id) {
        try {
            ApiResponse<StudentModel> studentResponse = getOneStudentById(id);
            if (studentResponse.httpStatus() != HttpStatus.OK) {
                return studentResponse;
            }
            StudentModel studentRes = studentResponse.data();
            studentRes.setDeletedAt(LocalDateTime.now());
            studentRepository.save(studentRes);
            return new ApiResponse<>("Student deleted", null, HttpStatus.OK, Status.SUCCESS);
        } catch (RuntimeException e) {
            return new ApiResponse<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR, Status.FAIL);
        }
    }

    @Transactional
    public ApiResponse<StudentModel> updateStudentById(Long id, CreateAndUpdateStudent studentBody) {
        try {
            if (studentRepository.existsByPhoneNumber(studentBody.phoneNumber())) {
                return new ApiResponse<>("Phone number already taken", null, HttpStatus.CONFLICT, Status.FAIL);
            }
            ApiResponse<StudentModel> studentResponse = getOneStudentById(id);
            Optional<MajorModel> classOptional = majorRepository.findById(studentBody.classId());
            if (studentResponse.httpStatus() != HttpStatus.OK) {
                return studentResponse;
            }
            StudentModel studentData = studentResponse.data();
            updateFieldIfNotNull(studentBody.address(), studentData::setAddress);
            updateFieldIfNotNull(studentBody.firstName(), studentData::setFirstName);
            updateFieldIfNotNull(studentBody.lastName(), studentData::setLastName);
            updateFieldIfNotNull(studentBody.dateOfBirth(), studentData::setDateOfBirth);
            if (classOptional.isEmpty())
                return new ApiResponse<>("Class not found", null, HttpStatus.NOT_FOUND, Status.FAIL);
            studentData.setStudentMajor(classOptional.get());
            updateFieldIfNotNull(studentBody.phoneNumber(), studentData::setPhoneNumber);
            studentData.setUpdatedAt(LocalDateTime.now());
            return new ApiResponse<>("Student updated", studentRepository.save(studentData), HttpStatus.OK, Status.SUCCESS);
        } catch (RuntimeException e) {
            return new ApiResponse<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR, Status.FAIL);
        }
    }

    private <T> void updateFieldIfNotNull(T value, java.util.function.Consumer<T> setter) {
        if (value != null && !(value instanceof String && ((String) value).isEmpty())) {
            setter.accept(value);
        }
    }

    public ApiResponse<List<StudentModel>> getStudentsByClassId(Long classId) {
        try {
            return new ApiResponse<>("Students by class", studentRepository.findAllByStudentMajorIdAndDeletedAtIsNull(classId), HttpStatus.OK, Status.SUCCESS);
        } catch (RuntimeException e) {
            return new ApiResponse<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR, Status.FAIL);
        }
    }

    public ApiResponse<List<StudentModel>> getStudentsByDepartment(Long departmentId) {
        try {
            return new ApiResponse<>("Students by department", studentRepository.findAllByStudentMajorDepartmentIdAndDeletedAtIsNull(departmentId), HttpStatus.OK, Status.SUCCESS);
        } catch (RuntimeException e) {
            return new ApiResponse<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR, Status.FAIL);
        }
    }

    public ApiResponse<List<StudentModel>> createStudentByUploadExcel(InputStream inputStream) {
        try {
            List<StudentModel> studentList = excelService.createStudents(inputStream);
            return new ApiResponse<>("Success", studentList, HttpStatus.CREATED, Status.SUCCESS);
        } catch (Exception e) {
            return new ApiResponse<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR, Status.FAIL);
        }
    }
}
