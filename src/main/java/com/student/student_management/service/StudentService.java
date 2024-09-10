package com.student.student_management.service;

import com.student.student_management.dto.ApiResponse;
import com.student.student_management.dto.CreateAndUpdateStudent;
import com.student.student_management.model.StudentModel;
import com.student.student_management.repository.StudentRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final CloudinaryService cloudinaryService;

    public ApiResponse<List<StudentModel>> getAllStudents() {
        return new ApiResponse<>("All students ", studentRepository.findAllByDeletedAtIsNull(), HttpStatus.OK);
    }

    public ApiResponse<StudentModel> getOneStudentById(Long id) {
        Optional<StudentModel> studenOptional = studentRepository.findById(id);
        return studenOptional.map(studentModel -> new ApiResponse<>("Student found", studentModel, HttpStatus.OK)).orElseGet(() -> new ApiResponse<>("Student not found", null, HttpStatus.NOT_FOUND));
    }

    public ApiResponse<StudentModel> createStudent(CreateAndUpdateStudent studentBody) {
        try {
            StudentModel studentModel = new StudentModel();
            studentModel.setCreatedAt(LocalDateTime.now());
            studentModel.setFirstName(studentBody.firstName());
            studentModel.setLastName(studentBody.lastName());
            studentModel.setDateOfBirth(studentBody.dateOfBirth());
            studentModel.setAddress(studentBody.address());
            studentModel.setClassId(studentBody.classId());
            studentModel.setPhoneNumber(studentBody.phoneNumber());
            studentModel.setPictureUrl(cloudinaryService.uploadFile(studentBody.picture()));
            StudentModel response = studentRepository.save(studentModel);
            return new ApiResponse<>("Student created", response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ApiResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
        }
    }

    public ApiResponse<StudentModel> deleteStudentById(Long id) {
        ApiResponse<StudentModel> studentResponse = getOneStudentById(id);
        if(studentResponse.status() != HttpStatus.OK){
            return studentResponse;
        }
        StudentModel studentRes = studentResponse.data();
        studentRes.setDeletedAt(LocalDateTime.now());
        studentRepository.save(studentRes);
        return new ApiResponse<>("Student deleted", null, HttpStatus.OK);
    }

    @Transactional
    public ApiResponse<StudentModel> updateStudentById(Long id, CreateAndUpdateStudent studentBody) {
        ApiResponse<StudentModel> studentResponse = getOneStudentById(id);
        if(studentResponse.status() != HttpStatus.OK){
            return studentResponse;
        }
        StudentModel studentData = studentResponse.data();
        updateFieldIfNotNull(studentBody.address(), studentData::setAddress);
        updateFieldIfNotNull(studentBody.firstName(), studentData::setFirstName);
        updateFieldIfNotNull(studentBody.lastName(), studentData::setLastName);
        updateFieldIfNotNull(studentBody.dateOfBirth(), studentData::setDateOfBirth);
        updateFieldIfNotNull(studentBody.classId(), studentData::setClassId);
        updateFieldIfNotNull(studentBody.phoneNumber(), studentData::setPhoneNumber);
        if (studentBody.picture() != null) {
            studentData.setPictureUrl(cloudinaryService.uploadFile(studentBody.picture()));
        }
        studentData.setUpdatedAt(LocalDateTime.now());
        return new ApiResponse<>("Student updated", studentRepository.save(studentData), HttpStatus.OK);
    }

    private <T> void updateFieldIfNotNull(T value, java.util.function.Consumer<T> setter) {
        if (value != null && !(value instanceof String && ((String) value).isEmpty())) {
            setter.accept(value);
        }
    }
}
