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

    private Optional<StudentModel> findStudentById(Long id) {
        return studentRepository.findById(id);
    }

    public ApiResponse<List<StudentModel>> getAllStudents() {
        return new ApiResponse<>("All students ", studentRepository.findAllByDeletedAtIsNull(), HttpStatus.OK);
    }

    public ApiResponse<StudentModel> getOneStudentById(Long id) {
        Optional<StudentModel> studenOptional = findStudentById(id);
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
            StudentModel response = studentRepository.save(studentModel);
            return new ApiResponse<>("Student created", response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ApiResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
        }
    }

    public ApiResponse<String> deleteStudentById(Long id) {
        Optional<StudentModel> studentOptional = findStudentById(id);
        if (studentOptional.isEmpty()) return new ApiResponse<>("Student not found", null, HttpStatus.NOT_FOUND);

        StudentModel studentResponse = studentOptional.get();
        studentResponse.setDeletedAt(LocalDateTime.now());
        studentRepository.save(studentResponse);
        return new ApiResponse<>("Student deleted", null, HttpStatus.OK);
    }

    @Transactional
    public ApiResponse<StudentModel> updateStudentById(Long id, CreateAndUpdateStudent studentBody) {
        Optional<StudentModel> studentOptional = findStudentById(id);
        if (studentOptional.isEmpty()) return new ApiResponse<>("Student not found", null, HttpStatus.NOT_FOUND);
        StudentModel studentResponse = studentOptional.get();
        if (studentBody.address() != null && !studentBody.address().isEmpty()) {
            studentResponse.setAddress(studentBody.address());
        }
        if (studentBody.firstName() != null && !studentBody.firstName().isEmpty()) {
            studentResponse.setFirstName(studentBody.firstName());
        }
        if (studentBody.lastName() != null && !studentBody.lastName().isEmpty()) {
            studentResponse.setLastName(studentBody.lastName());
        }
        if (studentBody.dateOfBirth() != null) {
            studentResponse.setDateOfBirth(studentBody.dateOfBirth());
        }
        if(studentBody.classId() != null){
            studentResponse.setClassId(studentBody.classId());
        }
        studentResponse.setUpdatedAt(LocalDateTime.now());
        return new ApiResponse<>("Student updated", studentRepository.save(studentResponse), HttpStatus.OK);
    }
}
